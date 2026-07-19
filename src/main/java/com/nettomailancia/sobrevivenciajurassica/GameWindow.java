package com.nettomailancia.sobrevivenciajurassica;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

// adicionar battleWindow assim que a lógica da classe batalha estiver arrumada

public class GameWindow extends JFrame {
    private final Game game;
    private final JPanel panel;
    private final Map<Character, Image> sprites = new HashMap<>();

    private static final int TILE_SIZE = 16;

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
        
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGame(g);
            }
        };

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
                    dispose();
                    return;
                }

                repaint();
            }
        });

        add(panel);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        panel.requestFocusInWindow();
    }

    private void drawGame(Graphics g) {
        char[] map = game.getVisibleMap();

        int mapWidth = game.getTilemap().getWidth();
        int mapHeight = game.getTilemap().getHeight();

        //g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
         Graphics2D grafic = (Graphics2D) g;
         
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                char c = map[y * mapWidth + x];
                
                Image sprite = sprites.getOrDefault(c, sprites.get(' '));
                
                grafic.drawImage(
                    sprite,
                    x * TILE_SIZE,
                    y * TILE_SIZE,
                    TILE_SIZE,
                    TILE_SIZE,
                    panel
                );

                
            /*  switch (c) {
                    case '#':
                        g.setColor(Color.GRAY);
                        break;
                    case '@':
                        g.setColor(Color.GREEN);
                        break;
                    case 'T':
                    case 'R':
                    case 'C':
                    case 'V':
                        g.setColor(Color.RED);
                        break;
                    default:
                        g.setColor(Color.WHITE);
                        break;
                }

                g.drawString(
                        Character.toString(c),
                        x * TILE_SIZE,
                        (y + 1) * TILE_SIZE
                );*/
            }
            
        }
        g.setColor(Color.YELLOW);
        int infoY = mapHeight * TILE_SIZE + 20;

        g.drawString("HP: " + game.getPlayer().getHp(), 10, infoY);

        infoY += 25;
        for (String message : game.getMessages()) {
            g.drawString(message, 10, infoY);
            infoY += 18;
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


    
}
