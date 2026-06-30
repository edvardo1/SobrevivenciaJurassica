/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 * @author ebm
 */
public class SobrevivenciaJurassica {
    public static void main(String[] args) {
        Game game = new Game();
        while (!game.quit()) {
            game.run();
        }
    }
}
