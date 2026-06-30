/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ebm
 */
abstract public class MapGenerator {
    static public void caveGenerate(Game game, int difficulty) throws Exception {
        Random r = new Random();
        TileMap tilemap = new TileMap(20, 10);
        Player player = new Player(difficulty);
        ArrayList<Position> freePositions = new ArrayList<Position>();

        for (int y = 0; y < tilemap.getHeight(); y += 1) {
            for (int x = 0; x < tilemap.getWidth(); x += 1) {
                int i = r.nextInt(100);
                if (i < 20) {
                    tilemap.setTile(x, y, new Wall());
                } else {
                    tilemap.setTile(x, y, new FreeTile());
                    freePositions.add(new Position(x, y));
                }
            }
        }

        ArrayList<Dinosaur> chosenDinos = new ArrayList<Dinosaur>();
        ArrayList<Supply> chosenItems = new ArrayList<Supply>();
        for (int i = 0; i < 5; i += 1) {
            chosenDinos.add(new Troodont());
        }
        for (int i = 0; i < 2; i += 1) {
            chosenDinos.add(new Compsognathus());
        }
        for (int i = 0; i < 2; i += 1) {
            chosenDinos.add(new Velociraptor());
        }
        chosenItems.add(new MedKit());
        chosenItems.add(new DinoTrap(new Compsognathus()));
        chosenItems.add(new DartGun());
        chosenItems.add(new Shock());

        for (Dinosaur dino : chosenDinos) {
            int index = r.nextInt(freePositions.size());
            Position position = freePositions.get(index);
            freePositions.remove(index);
            FreeTile t = (FreeTile) tilemap.getTile(position);
            dino.setPosition(position);
            t.setEntity(dino);
        }
        
        for (Supply supply : chosenItems) {
            int index = r.nextInt(freePositions.size());
            Position position = freePositions.get(index);
            freePositions.remove(index);
            FreeTile t = (FreeTile) tilemap.getTile(position);
            t.setSupply(supply);
        }

        if (tilemap.getTile(0, 0).isOccupied()) {
            throw new Exception("wow");
        }
        player = new Player(3);
        tilemap.setTile(0, 0, new FreeTile(player));
        player.setPosition(new Position(0, 0));

        if (tilemap.getTile(tilemap.getWidth() - 1, tilemap.getHeight() - 1).isOccupied()) {
            throw new Exception("wow");
        }
        tilemap.setTile(tilemap.getWidth() - 1, tilemap.getHeight() - 1, new FreeTile(new TRex()));
        
        game.setTilemap(tilemap);
        game.setPlayer(player);
    }
}
