/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author joaop
 */
abstract public class Dinosaur extends Entity {

    abstract String getName();

    public void tryMoveDir(Game game, Player p, TileMap tm, Direction d) {
        Position nextPosition = new Position(getPosition(), d);
        if (nextPosition.equals(getPosition()) && game.getBattle() == null) {
            game.setBattle(new Battle(p, this, true));
        }
    }

    public void think(Game game, Player p, TileMap tm) {
        Direction d = Direction.NORTH;
        int i = Rng.getInstance().dice(4);
        if (i == 1) {
            d = Direction.NORTH;
        } else if (i == 2) {
            d = Direction.SOUTH;
        } else if (i == 3) {
            d = Direction.EAST;
        } else if (i == 4) {
            d = Direction.SOUTH;
        }
        tryMoveDir(game, p, tm, d);
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
