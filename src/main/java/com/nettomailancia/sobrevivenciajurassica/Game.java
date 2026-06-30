/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

import java.util.ArrayList;
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
    private boolean onDebugMode = true;
    private ArrayList<Dinosaur> dinos;

    public Game() {
        boolean rerun = false;
        dinos = new ArrayList<Dinosaur>();
        do {
            rerun = false;
            try {
                MapGenerator.caveGenerate(this, 0);
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
            Position nextPosition = new Position(getPlayer().getPosition(), d);
            Tile tile = getTilemap().getTile(nextPosition);

            if (!tile.isOccupied()) {
                Supply item = ((FreeTile) tile).getSupply();
                boolean canMove = true;
                if (item != null) {
                    canMove = item.onPlayerMovement(this, player, nextPosition, (FreeTile) tile);
                }
                if (canMove) {
                    return getPlayer().move(nextPosition, getTilemap());
                } else {
                    return false;
                }
            } else {
                Entity e = tile.getEntity();
                if (e != null) {
                    Dinosaur dino = (Dinosaur) e;
                    setBattle(new Battle(getPlayer(), dino, false));
                    getBattle().turn();
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
        getBattle().turn();
    }

    private void fillChar(char[] c, Position p) {
        try {
            c[p.getY() * getTilemap().getWidth() + p.getX()] = getTilemap().getTile(p).getChar();
        } catch (Exception e) {
        }
    }

    private void castRay(Position orig, char[] c, double angle) {
        double x = orig.getX() + 0.5;
        double y = orig.getY() + 0.5;
        double dx = Math.cos(angle) * 0.1;
        double dy = Math.sin(angle) * 0.1;
        boolean running = true;
        while (Math.floor(x) == orig.getX() && Math.floor(y) == orig.getY()) {
            x += dx;
            y += dy;
        }

        while (running) {
            int fx = (int) Math.floor(x);
            int fy = (int) Math.floor(y);
            if (!(0 <= fx && fx < tilemap.getWidth())) {
                break;
            }
            if (!(0 <= fy && fy < tilemap.getHeight())) {
                break;
            }
            Position fpos = new Position(fx, fy);
            fillChar(c, fpos);
            try {
                if (tilemap.getTile(fpos).isOccupied()) {
                    running = false;
                }
            } catch (Exception e) {
                running = false;
            }
            x += dx;
            y += dy;
        }
    }

    private void showMap() {
        char[] c = new char[getTilemap().getWidth() * getTilemap().getHeight()];
        try {
            if (onDebugMode) {
                for (int y = 0; y < getTilemap().getHeight(); y += 1) {
                    for (int x = 0; x < getTilemap().getWidth(); x += 1) {
                        c[y * getTilemap().getWidth() + x] = getTilemap().getTile(new Position(x, y)).getChar();
                    }
                }
            } else {
                Position ppos = getPlayer().getPosition();
                int px = getPlayer().getPosition().getX();
                int py = getPlayer().getPosition().getY();
                for (int y = 0; y < getTilemap().getHeight(); y += 1) {
                    for (int x = 0; x < getTilemap().getWidth(); x += 1) {
                        c[y * getTilemap().getWidth() + x] = ' ';
                    }
                }

                for (double angle = 0.0; angle < 2 * 3.14; angle += 0.1) {
                    castRay(ppos, c, angle);
                }
                fillChar(c, getPlayer().getPosition());
            }
        } catch (Exception ex) {
        }

        for (int y = 0; y < getTilemap().getHeight(); y += 1) {
            for (int x = 0; x < getTilemap().getWidth(); x += 1) {
                char ch = c[y * getTilemap().getWidth() + x];
                if (ch == '#') {
                    System.out.print("\033[90m");
                } else {
                    System.out.print("\033[0m");
                }
                System.out.print(ch);
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public TileMap getTilemap() {
        return tilemap;
    }

    public void setTilemap(TileMap tilemap) {
        this.tilemap = tilemap;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public ArrayList<Dinosaur> getDinos() {
        return dinos;
    }

    public void setDinos(ArrayList<Dinosaur> dinos) {
        this.dinos = dinos;
    }

    public void run() {
        if (player.getHp() <= 0) {
            System.out.println("Voce esta morto!");
            running = false;
            return;
        }
        if (dinos.size() <= 0) {
            System.out.println("Voce conseguiu matar todos os dinossauros!");
            running = false;
            return;
        }

        if (getBattle() != null && getBattle().isOver()) {
            try {
                FreeTile ft = (FreeTile) getTilemap().getTile(getBattle().getFoe().getPosition());
                dinos.remove((Dinosaur) ft.getEntity());
                ft.setEntity(null);
            } catch (Exception e) {
            }
            setBattle(null);
        }

        showMap();

        if (getBattle() == null) {
            turnExplore();
        } else {
            System.out.println("is over: " + getBattle().isOver());
            turnBattle();
        }

        for (Dinosaur dino : dinos) {
            boolean isFoe = battle != null && battle.getFoe() == dino;
            if (!isFoe) {
                dino.think(this, player, tilemap);
            }
        }
    }
}
