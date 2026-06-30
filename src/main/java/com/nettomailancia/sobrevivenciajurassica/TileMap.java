/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author ebm
 */
public class TileMap {

    private Tile[] tiles;
    private int width;
    private int height;

    TileMap(int w, int h) throws Exception {
        if (w <= 0) {
            throw new Exception("invalid w");
        }
        if (h <= 0) {
            throw new Exception("invalid h");
        }
        width = w;
        height = h;
        tiles = new Tile[w * h];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile getTile(Position p) throws Exception {
        return getTile(p.getX(), p.getY());
    }

    public void setTile(Position p, Tile t) throws Exception {
        setTile(p.getX(), p.getY(), t);
    }

    public Tile getTile(int x, int y) throws Exception {
        if (0 <= y && y <= height && 0 <= x && x <= width) {
            return getTile(y * width + x);
        } else {
            throw new Exception("out of bounds");
        }
    }

    public void setTile(int x, int y, Tile t) throws Exception {
        if (0 <= y && y <= height && 0 <= x && x <= width) {
            setTile(y * width + x, t);
        } else {
            throw new Exception("out of bounds");
        }
    }

    public Tile getTile(int pos) throws Exception {
        if (0 <= pos && pos <= width * height) {
            return tiles[pos];
        } else {
            throw new Exception("out of bounds");
        }
    }

    public void setTile(int pos, Tile t) throws Exception {
        if (0 <= pos && pos < width * height) {
            tiles[pos] = t;
        } else {
            throw new Exception("out of bounds");
        }
    }
}
