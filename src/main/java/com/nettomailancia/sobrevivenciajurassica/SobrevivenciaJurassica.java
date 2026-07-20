package com.nettomailancia.sobrevivenciajurassica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SobrevivenciaJurassica {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sobrevivência Jurássica");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(600, 400));
            frame.setLayout(new BorderLayout());

            JLabel title = new JLabel("SOBREVIVÊNCIA JURÁSSICA");
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setFont(new Font("SansSerif", Font.BOLD, 28));

            JButton playButton = new JButton("Jogar");
            JButton exitButton = new JButton("Sair");

            JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 10));
            buttons.add(playButton);
            buttons.add(exitButton);

            frame.add(title, BorderLayout.NORTH);
            frame.add(buttons, BorderLayout.CENTER);

            playButton.addActionListener(e -> {
                Game game = new Game();
                GameWindow window = new GameWindow(game);
                game.setGameWindow(window);
                game.startDinoThreads();
            });

            exitButton.addActionListener(e -> System.exit(0));

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
