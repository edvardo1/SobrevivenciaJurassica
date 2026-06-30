/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author ebm
 */
public class Troodon extends Dinosaur {

    Troodon() {
        this.setHp(2);
    }

    @Override
    public boolean think(Game game, Player p, TileMap tm) {
        Direction d = null;
        int distance_x = p.getPosition().getX() - getPosition().getX();
        int distance_y = p.getPosition().getY() - getPosition().getY();
        if (Math.abs(distance_x) >= Math.abs(distance_y)) {
            if (distance_x > 0) {
                d = Direction.EAST;
            } else {
                d = Direction.WEST;
            }
        } else {
            if (distance_y > 0) {
                d = Direction.SOUTH;
            } else {
                d = Direction.NORTH;
            }
        }
        return tryMoveDir(game, p, tm, d);
    }

    @Override
    public char getChar() {
        return 'T';
    }

    @Override
    public String getName() {
        return "Troodonte";
    }
}
