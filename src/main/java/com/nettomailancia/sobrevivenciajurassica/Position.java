/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author ebm
 */
public class Position {

    private int x;
    private int y;
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Position(Position p) {
        x = p.x;
        y = p.y;
    }

    public Position(Position p, Direction d) {
        this(p);
        if (d != null) {
            switch (d) {
                case NORTH:
                    x -= 1;
                    break;
                case WEST:
                    y -= 1;
                    break;
                case SOUTH:
                    y += 1;
                    break;
                case EAST:
                    x += 1;
                    break;
                default:
                    break;
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
