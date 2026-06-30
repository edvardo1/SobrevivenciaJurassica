/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author joaop
 */
public class TRex extends Dinosaur {

    TRex() {
        this.setHp(3);
    }
    
        
    @Override
    public String getName() {
        return "T-Rex";
    }

    @Override
    public char getChar() {
        return 'R';
    }
}
