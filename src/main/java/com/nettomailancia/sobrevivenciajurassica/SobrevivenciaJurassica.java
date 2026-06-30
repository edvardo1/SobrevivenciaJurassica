/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.nettomailancia.sobrevivenciajurassica;

import java.util.Scanner;

/**
 * @author ebm
 */
public class SobrevivenciaJurassica {

    public static void main(String[] args) {
        while (true) {
            System.out.println("S O B R E V I V E N C I A");
            System.out.println("    J U R A S S I C A");
            System.out.println("1. Jogar");
            System.out.println("2. Sair");
            Scanner terminalInput = new Scanner(System.in);
            String s = terminalInput.nextLine();
            if ("1".equals(s)) {
                Game game = new Game();
                while (!game.quit()) {
                    game.run();
                }
            } else if ("2".equals(s)) {
                break;
            }
        }
    }
}
