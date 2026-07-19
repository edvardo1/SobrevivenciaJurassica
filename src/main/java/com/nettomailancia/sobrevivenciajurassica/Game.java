/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

import java.util.ArrayList;

/**
 *
 * @author joaop
 */
public class Game {

    private final ArrayList<String> messages = new ArrayList<>();
    private TileMap tilemap;
    private boolean running;
    private Player player;
    private Battle battle;
    private boolean onDebugMode = false;
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

    public void addMessage(String message) {
        messages.add(message);
        while (messages.size() > 12) {
            messages.remove(0);
        }
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void clearMessages() {
        messages.clear();
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
                    setBattle(new Battle(this, getPlayer(), dino, false));
                    getBattle().beginBattle();
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
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

    public char[] getVisibleMap() {
        char[] c = new char[tilemap.getWidth() * tilemap.getHeight()];

        try {
            if (onDebugMode) {
                for (int y = 0; y < tilemap.getHeight(); y++) {
                    for (int x = 0; x < tilemap.getWidth(); x++) {
                        Tile t = tilemap.getTile(new Position(x, y));
                        c[y * tilemap.getWidth() + x] = t.getChar();
                    }
                }
            } else {
                Position ppos = player.getPosition();

                for (int i = 0; i < c.length; i++) {
                    c[i] = ' ';
                }

                for (double angle = 0.0; angle < 2 * Math.PI; angle += 0.01) {
                    castRay(ppos, c, angle);
                }
                fillChar(c, player.getPosition());
            }
        } catch (Exception e) {
        }

        return c;
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

    public void update() {
        if (player.getHp() <= 0) {
            addMessage("Você está morto!");
            running = false;
            return;
        }
        if (dinos.size() <= 0) {
            addMessage("Você conseguiu matar todos os dinossauros!");
            running = false;
            return;
        }

        if (battle != null && battle.isOver()) {
            try {
                FreeTile ft = (FreeTile) tilemap.getTile(battle.getFoe().getPosition());
                dinos.remove((Dinosaur) ft.getEntity());
                ft.setEntity(null);
            } catch (Exception e) {
            }
            battle = null;
        }

        if (battle != null && battle.isTryingToRunAway()) {
            Direction d = null;

            switch (Rng.getInstance().dice(4)) {
                case 1 ->
                    d = Direction.NORTH;
                case 2 ->
                    d = Direction.WEST;
                case 3 ->
                    d = Direction.EAST;
                case 4 ->
                    d = Direction.SOUTH;
            }

            if (tryMove(d)) {
                addMessage("Você conseguiu escapar!");
                battle = null;
            }
        }
        for (Dinosaur dino : dinos) {
            boolean isFoe = battle != null && battle.getFoe() == dino;
            if (!isFoe) {
                dino.think(this, player, tilemap);
            }
        }
    }

    public void playerInput(char key) {
        if (battle == null) {
            switch (key) {
                case 'w':
                    tryMove(Direction.NORTH);
                    addMessage("NORTE");
                    break;
                case 'a':
                    tryMove(Direction.WEST);
                    addMessage("OESTE");
                    break;
                case 's':
                    tryMove(Direction.SOUTH);
                    addMessage("SUL");
                    break;
                case 'd':
                    tryMove(Direction.EAST);
                    addMessage("LESTE");
                    break;
                case 'c':
                    
                    if( player.getMedkits() > 0 ) {
                        addMessage("CURA");
                        player.tryHeal();
                    }else{
                        addMessage("SEM MEDKITS");
                    }
                    break;
                case 'D':
                    addMessage("toggle debug");
                    onDebugMode = !onDebugMode;
                    break;
                case 'q':
                    running = false;
                    return;
            }
        } else {
            battle.playerInput(key);
        }
        update();
    }
}
