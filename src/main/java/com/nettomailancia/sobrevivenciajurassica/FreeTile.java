/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author ebm
 */
public class FreeTile extends Tile {
    Entity entity;
    Supply supply;
    
    public FreeTile(Entity e, Supply s) {
        entity = e;
        supply = s;
    }
    
    public FreeTile(Entity e) {
        this(e, null);
    }
    
    public FreeTile(Supply s) {
        this(null, s);
    }
    
    public FreeTile() {
        this(null, null);
    }
    
    @Override
    char getChar() {
        if (entity != null) {
            return entity.getChar();
        } else if (supply != null) {
            return 'X';
        } else {
            return '.';
        }
    }
}
