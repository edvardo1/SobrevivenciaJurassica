/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author ebm
 */
public class Troodon extends Dinosaur {

    Troodon() {
        this.setHp(2);
    }

    @Override
    public char getChar() {
        return 'T';
    }
    
    @Override
    public String getName() {
        return "Troodonte";
    }

    void move() {

    }
}
