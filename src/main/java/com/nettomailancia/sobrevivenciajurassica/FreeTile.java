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
    private Entity entity;
    private Supply supply;
    
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
    
    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }
    
    @Override
    char getChar() {
        if (getEntity() != null) {
            return getEntity().getChar();
        } else if (getSupply() != null) {
            return 'X';
        } else {
            return '.';
        }
    }
}
