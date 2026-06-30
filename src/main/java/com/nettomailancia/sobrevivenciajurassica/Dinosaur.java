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

    @Override
    boolean move(Direction d, TileMap tm) {
        return false;
    }

    @Override
    void damage(int d) {
        this.hp -= d;
    }
}
