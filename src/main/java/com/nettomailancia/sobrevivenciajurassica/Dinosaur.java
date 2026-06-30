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

    void damageHand() {
        int points = Rng.getInstance().dice(6);
        if (points == 6) {
            this.setHp(this.getHp() - 2);
        } else if (points > 2) {
            this.setHp(this.getHp() - 1);
        }
    }

    void damageShockBaton() {
        int points = Rng.getInstance().dice(6);
        if (points == 6) {
            this.setHp(this.getHp() - 2);
        } else if (points == 1) {
            this.setHp(this.getHp() - 1);
        }
    }

    void damageDart() {
        this.setHp(this.getHp() - 2);
    }
}
