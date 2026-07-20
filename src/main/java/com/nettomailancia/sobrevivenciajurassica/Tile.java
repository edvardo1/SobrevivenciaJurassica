/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author ebm
 */
abstract public class Tile {

    abstract char getChar();

    abstract boolean isOccupied();

    public Entity getEntity() {
        return null;
    }

    public Supply getSupply() {
        return null;
    }
}
