package com.nettomailancia.sobrevivenciajurassica;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.Timer;

// adicionar battleWindow assim que a lógica da classe batalha estiver arrumada
public class GameWindow extends JFrame {

    private final Game game;
    private final JPanel panel;
    private final Map<Character, Image> sprites = new HashMap<>();

    private JButton attackButton;
    private JButton itemButton;
    private JButton runButton;
    private JPanel battleButtonsPanel;

    private final Map<String, Image> actionSprites = new HashMap<>();
    private String currentAction;

    private static final int TILE_SIZE = 50;

    public GameWindow(Game game) {
        this.game = game;

        setTitle("Sobrevivência Jurássica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        sprites.put('#', loadImage("/sprites/wall.png"));
        sprites.put('@', loadImage("/sprites/player.png"));
        sprites.put('T', loadImage("/sprites/troodon.png"));
        sprites.put('R', loadImage("/sprites/trex.png"));
        sprites.put('C', loadImage("/sprites/compsognathus.png"));
        sprites.put('V', loadImage("/sprites/velociraptor.png"));
        sprites.put('X', loadImage("/sprites/supply.png"));
        sprites.put(' ', loadImage("/sprites/freetile.png"));
        actionSprites.put("punch", loadImage("/sprites/punch.png"));
        actionSprites.put("shock", loadImage("/sprites/shock.png"));
        actionSprites.put("dart", loadImage("/sprites/dart.png"));
        actionSprites.put("bite", loadImage("/sprites/bite.png"));

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (game.getBattle() != null) {
                    drawBattle(g);
                } else {
                    drawGame(g);
                }
            }
        };

        battleButtonsPanel = new JPanel();
        attackButton = new JButton("Atacar");
        itemButton = new JButton("Arma de Dardos");
        runButton = new JButton("Fugir");

        attackButton.addActionListener(e -> handleBattleAction('p'));
        itemButton.addActionListener(e -> handleBattleAction('d'));
        runButton.addActionListener(e -> handleBattleAction('r'));

        battleButtonsPanel.add(attackButton);
        battleButtonsPanel.add(itemButton);
        battleButtonsPanel.add(runButton);
        battleButtonsPanel.setVisible(false); // só aparece durante a batalha

        add(battleButtonsPanel, java.awt.BorderLayout.SOUTH);

        panel.setBackground(Color.BLACK);
        panel.setFocusable(true);

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        game.playerInput('w');
                        break;
                    case KeyEvent.VK_A:
                        game.playerInput('a');
                        break;
                    case KeyEvent.VK_S:
                        game.playerInput('s');
                        break;
                    case KeyEvent.VK_D:
                        game.playerInput('d');
                        break;
                    case KeyEvent.VK_C:
                        game.playerInput('c');
                        break;
                    case KeyEvent.VK_Q:
                        game.playerInput('q');
                        dispose();
                        return;
                    case KeyEvent.VK_G:
                        game.playerInput('D');
                        break;
                    case KeyEvent.VK_P:
                        game.playerInput('p');
                        break;
                    case KeyEvent.VK_R:
                        game.playerInput('r');
                        break;
                }

                if (game.quit()) {
                    game.stopDinoThreads();
                    dispose();
                    return;
                }
                battleButtonsPanel.setVisible(game.getBattle() != null);
                repaint();
            }
        });

        add(panel);

        setSize(1280, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        Timer repaintTimer = new Timer(16, e -> panel.repaint());
        repaintTimer.start();

        panel.requestFocusInWindow();
    }

    private void tryDraw(Graphics g, Entity e, Set<Position> positions) {
        final Position curr = e.getPosition();
        final Position last = e.getLastPosition();
        final boolean inCurr = positions.contains(curr);
        final boolean inLast = positions.contains(last);
        if (inCurr || inLast) {
            double t = (System.currentTimeMillis() - e.getMoveStartTime())
                    / (double) Entity.MOVE_DURATION_MS;

            t = Math.min(1.0, Math.max(0.0, t));

            double x = last.getX() + (curr.getX() - last.getX()) * t;
            double y = last.getY() + (curr.getY() - last.getY()) * t;

            g.drawImage(
                    sprites.get(e.getChar()),
                    (int) (x * TILE_SIZE),
                    (int) (y * TILE_SIZE),
                    TILE_SIZE,
                    TILE_SIZE,
                    panel
            );
        }
    }

    private void drawGame(Graphics g) {
        int mapHeight = game.getTilemap().getHeight();
        TileMap tilemap = game.getTilemap();

        Set<Position> positions = game.getVisibleMap2();
        for (Position position : positions) {
            try {
                final Image image;
                Tile t = tilemap.getTile(position);
                if (t.getChar() == '#') {
                    image = sprites.get('#');
                } else {
                    if (t.getSupply() != null) {
                        image = sprites.get('X');
                    } else {
                        image = sprites.get(' ');
                    }
                }
                g.drawImage(
                        image,
                        position.getX() * TILE_SIZE,
                        position.getY() * TILE_SIZE,
                        TILE_SIZE,
                        TILE_SIZE,
                        panel
                );
            } catch (Exception ignored) {
            }
        }

        for (Dinosaur d : game.getDinos()) {
            tryDraw(g, d, positions);
        }
        tryDraw(g, game.getPlayer(), positions);
        g.setColor(Color.YELLOW);
        int infoY = mapHeight * TILE_SIZE + 20;

        g.drawString("HP: " + game.getPlayer().getHp(), 10, infoY);

        infoY += 25;
        for (String message : game.getMessages()) {
            g.drawString(message, 10, infoY);
            infoY += 18;
        }
    }

    public void drawBattle(Graphics g) {
        Graphics2D d2 = (Graphics2D) g;
        Battle battle = game.getBattle();
        Dinosaur foe = battle.getFoe();
        Player player = game.getPlayer();

        d2.setColor(new Color(20, 60, 20));
        d2.fillRect(0, 0, getWidth(), getHeight());

        Image foeSprite = sprites.get(foe.getChar());
        d2.drawImage(foeSprite, 500, 60, 96, 96, panel);
        d2.setColor(Color.WHITE);
        d2.drawString(foe.getName(), 500, 50);
        // adicionar barra de vida pros inimigos? maior parte das vezes morre com 1 soco
        Image playerSprite = sprites.get('@');
        d2.drawImage(playerSprite, 100, 260, 96, 96, panel);
        d2.drawString("Você", 100, 250);
        drawHpBar(d2, 100, 360, player.getHp(), player.MAX_HP);

        if (currentAction != null) {
            Image actionImg = actionSprites.get(currentAction);
            if (actionImg != null) {
                int midX = getWidth() / 2 - 24;
                int midY = getHeight() / 2 - 60;
                d2.drawImage(actionImg, midX, midY, 48, 48, panel);
            }
        }

        int msgY = 420;
        for (String message : battle.battleLog) {
            d2.drawString(message, 20, msgY);
            msgY += 18;
        }
    }

    private void drawHpBar(Graphics2D g2, int x, int y, int hp, int maxHp) {
        int barWidth = 100;
        int filled = (int) (barWidth * ((double) hp / maxHp));
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x, y, barWidth, 10);
        g2.setColor(hp < maxHp / 4 ? Color.RED : Color.GREEN);
        g2.fillRect(x, y, filled, 10);
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, barWidth, 10);
    }

    private void handleBattleAction(char key) {
        game.playerInput(key);
        if (game.quit()) {
            game.stopDinoThreads();
            dispose();
            return;
        } else {
            if (game.getBattle() != null) {
                playAttackSequence(game.getBattle().getPlayerAction(), game.getBattle().getDinoAction());
            }
            battleButtonsPanel.setVisible(game.getBattle() != null);
            repaint();
        }
    }

    private Image loadImage(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new RuntimeException("Sprite não encontrado: " + path);
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar sprite: " + path, e);
        }
    }

    private void playAttackSequence(String playerAction, String foeAction) {
        currentAction = playerAction;
        repaint();

        Timer timer1 = new Timer(1000, e -> {
            ((Timer) e.getSource()).stop();
            currentAction = foeAction;
            repaint();

            Timer timer2 = new Timer(1000, e2 -> {
                ((Timer) e2.getSource()).stop();
                currentAction = null;
                repaint();
            });
            timer2.setRepeats(false);
            timer2.start();
        });
        timer1.setRepeats(false);
        timer1.start();
    }
}
