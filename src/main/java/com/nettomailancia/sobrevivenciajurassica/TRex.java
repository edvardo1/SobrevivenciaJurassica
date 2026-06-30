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
    public boolean think(Game game, Player p, TileMap tm) {
        /* dumb */
        return false;
    }
        
    @Override
    public String getName() {
        return "T-Rex";
    }

    @Override
    public char getChar() {
        return 'R';
    }
    
    @Override
    public void damageHand() {
        System.out.println("T-rex possui uma pele muito grossa, seu soco não causa dano!");
    }
}
