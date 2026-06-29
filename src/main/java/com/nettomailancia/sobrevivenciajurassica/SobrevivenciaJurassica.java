/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.nettomailancia.sobrevivenciajurassica;

import java.util.Random;

/**
 * @author ebm
 */
public class SobrevivenciaJurassica {
    private TileMap tilemap;
    private boolean running;
    
    SobrevivenciaJurassica() {
        try {
            tilemap = new TileMap(20, 10);

            for (int y = 0; y < tilemap.getHeight(); y += 1) {
                for (int x = 0; x < tilemap.getWidth(); x += 1) {
                    Random r = new Random();
                    int i = r.nextInt(100);
                    if (i < 20) {
                        tilemap.setTile(x, y, new Wall());
                    } else {
                        tilemap.setTile(x, y, new FreeTile());
                    }
                }
            }
        } catch (Exception ex) {
            /* woah so safe */
        }
        running = true;
    }   
    
    public boolean quit() {
        return !running;
    }
    
    public void run() {
        try {
            for (int y = 0; y < tilemap.getHeight(); y += 1) {
                for (int x = 0; x < tilemap.getWidth(); x += 1) {
                    System.out.print(tilemap.getTile(x, y).getChar());
                }
                System.out.print("\n");
            }
            System.out.print("\n\n");
            running = false;
        } catch (Exception ex) {
        }
    }
    
    public static void main(String[] args) {
        SobrevivenciaJurassica sj = new SobrevivenciaJurassica();
        while (!sj.quit()) {
            sj.run();
        }
    }
}
