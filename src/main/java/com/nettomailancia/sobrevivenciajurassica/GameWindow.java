package com.nettomailancia.sobrevivenciajurassica;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class GameWindow extends JFrame {

    private final Game game;
    private final JPanel panel;
    private BufferedImage trexImage;
    private BufferedImage grassImage;
    private BufferedImage treesImage;
    private BufferedImage alanGrantImage;

    private static final int TILE_SIZE = 45;

    public GameWindow(Game game) {
        this.game = game;

        setTitle("Sobrevivência Jurássica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            trexImage = ImageIO.read(getClass().getResource("/trex.png"));
            grassImage = ImageIO.read(getClass().getResource("/grass.png"));
            treesImage = ImageIO.read(getClass().getResource("/trees.png"));
            alanGrantImage = ImageIO.read(getClass().getResource("/alangrant.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        setSize(1280, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        panel.requestFocusInWindow();
    }

    private void drawGame(Graphics g) {
        char[] map = game.getVisibleMap();

        int mapWidth = game.getTilemap().getWidth();
        int mapHeight = game.getTilemap().getHeight();

        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 32));

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                char c = map[y * mapWidth + x];

                if (c != '#' && c != ' ') {
                    g.drawImage(
                            grassImage,
                            x * TILE_SIZE,
                            y * TILE_SIZE,
                            TILE_SIZE,
                            TILE_SIZE,
                            null
                    );
                }

                switch (c) {
                    case '#':
                        g.drawImage(
                                treesImage,
                                x * TILE_SIZE,
                                y * TILE_SIZE,
                                TILE_SIZE,
                                TILE_SIZE,
                                null
                        );
                        continue;
                    case '@':
                        g.drawImage(
                                alanGrantImage,
                                x * TILE_SIZE,
                                y * TILE_SIZE,
                                TILE_SIZE,
                                TILE_SIZE,
                                null
                        );
                        continue;
                    case 'R':
                    case 'T':
                    case 'C':
                    case 'V':
                        g.drawImage(
                                trexImage,
                                x * TILE_SIZE,
                                y * TILE_SIZE,
                                TILE_SIZE,
                                TILE_SIZE,
                                null
                        );
                        continue;
                    default:
                        
                        break;
                }
            }
        }
        g.setColor(Color.YELLOW);
        int infoY = mapHeight * TILE_SIZE + 20;

        g.drawString("HP: " + game.getPlayer().getHp(), 10, infoY);

        infoY += 25;
        for (String message : game.getMessages()) {
            g.drawString(message, 10, infoY);
            infoY += 32;
        }
    }
}
