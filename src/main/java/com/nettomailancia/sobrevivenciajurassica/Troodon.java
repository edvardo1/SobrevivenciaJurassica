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
                System.out.println("moving east");
            } else {
                d = Direction.WEST;
                System.out.println("moving west");
            }
        } else {
            if (distance_y > 0) {
                d = Direction.SOUTH;
                System.out.println("moving south");
            } else {
                d = Direction.NORTH;
                System.out.println("moving north");
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
