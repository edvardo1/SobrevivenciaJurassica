/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

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
    private final Object worldLock = new Object();
    private GameWindow gameWindow;
    private int difficulty;
    private long seed;

    public Object getWorldLock() {
        return worldLock;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public long getSeed() {
        return seed;
    }

    public Game(int difficulty) {
        this(difficulty, System.currentTimeMillis());
    }

    public Game(int difficulty, long seed) {
        this.difficulty = difficulty;
        this.seed = seed;
        Rng.getInstance().setSeed(seed);
        boolean rerun = false;
        dinos = new ArrayList<Dinosaur>();
        do {
            rerun = false;
            try {
                MapGenerator.caveGenerate(this, difficulty);
            } catch (Exception ex) {
                System.out.println("failed!, doing it again!");
                rerun = true;
            }
        } while (rerun);
        running = true;
    }

    public void startDinoThreads() {
        for (Dinosaur dino : dinos) {
            dino.startThread(this, player, tilemap);
        }
    }

    public void stopDinoThreads() {
        synchronized (worldLock) {
            worldLock.notifyAll(); // acorda qualquer thread presa em wait()
        }
        for (Dinosaur dino : dinos) {
            dino.stopThread();
        }
    }

    public boolean quit() {
        return !running;
    }

    public void addMessage(String message) {
        messages.add(message);
        while (messages.size() > 6) {
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

    private void castRay(Position orig, Set<Position> positions, double angle) {
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
            positions.add(fpos);
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

    public Set<Position> getVisibleMap() {
        Set<Position> positions = new HashSet<>();

        try {
            if (onDebugMode) {
                for (int y = 0; y < tilemap.getHeight(); y++) {
                    for (int x = 0; x < tilemap.getWidth(); x++) {
                        positions.add(new Position(x, y));
                    }
                }
            } else {
                Position ppos = player.getPosition();

                for (double angle = 0.0; angle < 2 * Math.PI; angle += 0.01) {
                    castRay(ppos, positions, angle);
                }
                positions.add(ppos);
            }
        } catch (Exception e) {
        }

        return positions;
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
        synchronized (worldLock) {

            if (player.getHp() <= 0) {
                running = false;
                stopDinoThreads();

                int option = javax.swing.JOptionPane.showOptionDialog(
                        gameWindow,
                        "Você perdeu!",
                        "Fim de jogo",
                        javax.swing.JOptionPane.DEFAULT_OPTION,
                        javax.swing.JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Reiniciar Jogo", "Novo Jogo"},
                        "Reiniciar Jogo");

                gameWindow.dispose();

                if (option == 0) {
                    SobrevivenciaJurassica.restartGame(this);
                } else {
                    SobrevivenciaJurassica.showMainMenu();
                }

                return;
            }

            if (dinos.isEmpty()) {
                running = false;
                stopDinoThreads();

                int option = javax.swing.JOptionPane.showOptionDialog(
                        gameWindow,
                        "Você venceu!",
                        "Fim de jogo",
                        javax.swing.JOptionPane.DEFAULT_OPTION,
                        javax.swing.JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Reiniciar Jogo", "Novo Jogo"},
                        "Reiniciar Jogo");

                gameWindow.dispose();

                if (option == 0) {
                    SobrevivenciaJurassica.restartGame(this);
                } else {
                    SobrevivenciaJurassica.showMainMenu();
                }

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
                worldLock.notifyAll();
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
                    worldLock.notifyAll();
                }
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

                    if (player.getMedkits() > 0) {
                        addMessage("CURA");
                        player.tryHeal();
                    } else {
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
