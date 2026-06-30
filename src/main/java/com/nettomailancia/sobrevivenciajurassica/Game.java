/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author joaop
 */
public class Game {

    private TileMap tilemap;
    private boolean running;
    private Player player;
    private Battle battle;
    private boolean onDebugMode;

    private void generate(int difficulty) throws Exception {
        Random r = new Random();
        tilemap = new TileMap(20, 10);
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
            Position position = freePositions.get(index);
            freePositions.remove(index);
            FreeTile t = (FreeTile) tilemap.getTile(position);
            dino.setPosition(position);
            t.setEntity(dino);

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
    }

    public Game() {
        boolean rerun = false;
        do {
            rerun = false;
            try {
                generate(0);
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

    public boolean tryMove(Direction d) {
        try {
            Position nextPosition = new Position(player.getPosition(), d);
            Tile tile = tilemap.getTile(nextPosition);

            if (!tile.isOccupied()) {
                return player.move(nextPosition, tilemap);
            } else {
                Entity e = tile.getEntity();
                if (e != null) {
                    Dinosaur dino = (Dinosaur) e;
                    battle = new Battle(player, dino, false);
                    battle.turn();
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("exception");
            return false;
        }
        return false;
    }

    private void turnBattle() {
        battle.turn();
    }

    private void fillChar(char[] c, Position p) {
        try {
            c[p.getY() * tilemap.getWidth() + p.getX()] = tilemap.getTile(p).getChar();
        } catch (Exception e) {
        }
    }

    private void fillChars(char[] c, Direction d, Position orig) {
        try {
            Position p = new Position(orig);
            p.go(d);
            while (!tilemap.getTile(p).isOccupied()) {
                fillChar(c, p);
                p.go(d);
            }
            fillChar(c, p);
        } catch (Exception e) {
            System.out.println("[ERROR] exception!");
        }
    }

    private void showMap() {
        char[] c = new char[tilemap.getWidth() * tilemap.getHeight()];
        try {
            if (onDebugMode) {
                for (int y = 0; y < tilemap.getHeight(); y += 1) {
                    for (int x = 0; x < tilemap.getWidth(); x += 1) {
                        c[y * tilemap.getWidth() + x] = tilemap.getTile(new Position(x, y)).getChar();
                    }
                }
            } else {
                for (int y = 0; y < tilemap.getHeight(); y += 1) {
                    for (int x = 0; x < tilemap.getWidth(); x += 1) {
                        c[y * tilemap.getWidth() + x] = ' ';
                    }
                }

                fillChar(c, player.getPosition());
                fillChars(c, Direction.NORTH, player.getPosition());
                fillChars(c, Direction.WEST, player.getPosition());
                fillChars(c, Direction.SOUTH, player.getPosition());
                fillChars(c, Direction.EAST, player.getPosition());
            }
        } catch (Exception ex) {
        }

        for (int y = 0; y < tilemap.getHeight(); y += 1) {
            for (int x = 0; x < tilemap.getWidth(); x += 1) {
                System.out.print(c[y * tilemap.getWidth() + x]);
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }

    private void turnExplore() {
        System.out.println("Opcoes:");
        System.out.println("w. Norte");
        System.out.println("a. Leste");
        System.out.println("s. Sul");
        System.out.println("d. Oeste");
        System.out.println("c. Cura");
        System.out.println("D. Debug");
        System.out.println("q. Sair");
        Scanner terminalInput = new Scanner(System.in);
        String s = terminalInput.nextLine();
        if (s.equals("w")) {
            tryMove(Direction.NORTH);
        } else if (s.equals("a")) {
            tryMove(Direction.WEST);
        } else if (s.equals("s")) {
            tryMove(Direction.SOUTH);
        } else if (s.equals("d")) {
            tryMove(Direction.EAST);
        } else if (s.equals("q")) {
            running = false;
        } else if (s.equals("D")) {
            onDebugMode = !onDebugMode;
        }
    }

    public void run() {
        if (battle != null && battle.isOver()) {
            try {
                FreeTile ft = (FreeTile) tilemap.getTile(battle.getFoe().getPosition());
                ft.setEntity(null);
            } catch (Exception e) {
            }
            battle = null;
        }
        
        showMap();

        if (battle == null) {
            turnExplore();
        } else {
            System.out.println("is over: " + battle.isOver());
            turnBattle();
        }
    }
}
