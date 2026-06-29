/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.nettomailancia.sobrevivenciajurassica;

import java.util.Random;
import java.util.ArrayList;

/**
 * @author ebm
 */
public class SobrevivenciaJurassica {

    private TileMap tilemap;
    private boolean running;
    private Player player;

    private void generateGame(int difficulty) throws Exception {
        Random r = new Random();
        tilemap = new TileMap(20, 10);

        for (int y = 0; y < tilemap.getHeight(); y += 1) {
            for (int x = 0; x < tilemap.getWidth(); x += 1) {
                int i = r.nextInt(100);
                if (i < 20) {
                    tilemap.setTile(x, y, new Wall());
                } else {
                    tilemap.setTile(x, y, new FreeTile());
                }
            }
        }

        ArrayList<Integer> freePositions = new ArrayList<Integer>();
        for (int index = 0; index < tilemap.getHeight() * tilemap.getWidth(); index += 1) {
            Tile t = tilemap.getTile(index);
            if (!t.isOccupied()) {
                freePositions.add(index);
            }
        }
        ArrayList<Dinosaur> chosenDinos = new ArrayList<Dinosaur>();
        for (int i = 0; i < 5; i += 1) {
            chosenDinos.add(new Troodont());
        }
        for (int i = 0; i < 2; i += 1) {
            chosenDinos.add(new Compsognathus());
        }
        for (int i = 0; i < 2; i += 1) {
            chosenDinos.add(new Velociraptor());
        }
        for (Dinosaur dino : chosenDinos) {
            int index = r.nextInt(freePositions.size());
            int position = freePositions.get(index);
            freePositions.remove(index);
            FreeTile t = (FreeTile) tilemap.getTile(position);
            t.setEntity(dino);
        }

        if (tilemap.getTile(0, 0).isOccupied()) {
            throw new Exception("wow");
        }
        player = new Player(0, 0, difficulty);
        tilemap.setTile(0, 0, new FreeTile(player));

        if (tilemap.getTile(tilemap.getWidth() - 1, tilemap.getHeight() - 1).isOccupied()) {
            throw new Exception("wow");
        }
        tilemap.setTile(tilemap.getWidth() - 1, tilemap.getHeight() - 1, new FreeTile(new TRex()));
    }

    SobrevivenciaJurassica() {
        boolean rerun = false;
        do {
            rerun = false;
            try {
                generateGame(0);
            } catch (Exception ex) {
                System.out.println("failed!, doing it again!");
                rerun = true;
            }
        } while (rerun);
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
