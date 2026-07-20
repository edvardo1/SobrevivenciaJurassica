/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

import javax.swing.SwingUtilities;

/**
 *
 * @author joaop
 */
abstract public class Dinosaur extends Entity implements Runnable {

    abstract String getName();

    private volatile boolean alive = true;
    private Thread thread;
    private Game game;
    private Player player;
    private TileMap tilemap;

    public void startThread(Game game, Player player, TileMap tilemap) {
        this.game = game;
        this.player = player;
        this.tilemap = tilemap;

        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public void stopThread() {
        alive = false;
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    public void run() {
        while (alive && !game.quit()) {
            synchronized (game.getWorldLock()) {
                while (game.getBattle() != null && alive && !game.quit()) {
                    try {
                        game.getWorldLock().wait();
                    } catch (InterruptedException e) {
                        alive = false;
                        break;
                    }
                }

                if (!alive || game.quit()) {
                    break;
                }

                if (getHp() <= 0) {
                    alive = false;
                    continue;
                }

                think(game, player, tilemap);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public boolean tryMoveDir(Game game, Player p, TileMap tm, Direction d) {
        Position nextPosition = new Position(getPosition(), d);
        boolean inX = 0 <= nextPosition.getX() && nextPosition.getX() < tm.getWidth();
        boolean inY = 0 <= nextPosition.getY() && nextPosition.getY() < tm.getHeight();
        if (inX && inY) {
            if (nextPosition.equals(p.getPosition()) && game.getBattle() == null) {
                game.setBattle(new Battle(game, p, this, true));
                return false;
            } else {
                FreeTile ft = null, currentFt = null;
                try {
                    Tile t = tm.getTile(nextPosition);
                    if (!t.isOccupied()) {
                        ft = (FreeTile) t;
                        currentFt = (FreeTile) tm.getTile(getPosition());
                    }
                } catch (Exception e) {
                }
                if (ft != null && currentFt != null) {
                    ft.setEntity(this);
                    assert (currentFt.getEntity() == this);
                    currentFt.setEntity(null);
                    setPosition(nextPosition);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean think(Game game, Player p, TileMap tm) {
        Direction d = null;
        int i = Rng.getInstance().dice(4);
        if (i == 1) {
            d = Direction.NORTH;
        } else if (i == 2) {
            d = Direction.WEST;
        } else if (i == 3) {
            d = Direction.EAST;
        } else if (i == 4) {
            d = Direction.SOUTH;
        }
        assert (d != null);
        return tryMoveDir(game, p, tm, d);
    }

    public void attackPlayer(Player player) {
        player.setHp(player.getHp() - 1);
    }

    public void damageHand() {
        int points = Rng.getInstance().dice(6);
        if (points == 6) {
            this.setHp(this.getHp() - 2);
        } else if (points > 2) {
            this.setHp(this.getHp() - 1);
        }
    }

    public void damageShockBaton() {
        int points = Rng.getInstance().dice(6);
        if (points == 6) {
            this.setHp(this.getHp() - 2);
        } else if (points == 1) {
            this.setHp(this.getHp() - 1);
        }
    }

    public void damageDart() {
        this.setHp(this.getHp() - 2);
    }
}
