/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author joaop
 */
abstract public class Entity {

    private Position position;
    private int hp;

    public Entity() {
    }

    public Entity(Position p, int hp) {
        this.hp = hp;
        position = p;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position p) {
        position = p;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    private FreeTile getCurrentFreeTile(TileMap tm) throws Exception {
        return (FreeTile) tm.getTile(position);
    }

    public boolean move(Position newPosition, TileMap tm) {
        try {
            FreeTile currentTile = getCurrentFreeTile(tm);

            boolean inBoundsX = 0 <= newPosition.getX() && newPosition.getX() < tm.getWidth();
            boolean inBoundsY = 0 <= newPosition.getY() && newPosition.getY() < tm.getHeight();
            if (!inBoundsX || !inBoundsY) {
                return false;
            }
            Tile nextTile = tm.getTile(newPosition);
            if (nextTile == null) {
                throw new Exception("what");
            }
            if (nextTile.isOccupied()) {
                return false;
            }
            FreeTile nextFreeTile = (FreeTile) nextTile;

            position = new Position(newPosition);
            currentTile.setEntity(null);
            nextFreeTile.setEntity(this);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    abstract char getChar();

}
