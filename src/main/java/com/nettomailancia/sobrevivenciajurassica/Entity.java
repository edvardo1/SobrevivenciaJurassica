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
    int hp;
    
    abstract void move();
    abstract void damage(int d);
    abstract char getChar();

    int getHp() {
        return hp;
    }
    void setHp(int hp) {
        this.hp = hp;
    }
}
