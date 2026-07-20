/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

import java.util.Objects;

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
        go(d);
    }

    public void go(Direction d) {
        if (d != null) {
            switch (d) {
                case NORTH:
                    y -= 1;
                    break;
                case WEST:
                    x -= 1;
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

    public boolean equals(Position other) {
        return x == other.x && y == other.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.equals((Position) o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position{x=" + x + ", y=" + y + "}";
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
