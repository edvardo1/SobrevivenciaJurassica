/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author joaop
 */
public class Velociraptor extends Dinosaur {

    Velociraptor() {
        this.setHp(2);
    }

    public boolean think(Game game, Player p, TileMap tm) {
        boolean can_move_again = super.think(game, p, tm);
        if (can_move_again) {
            return super.think(game, p, tm);
        }
        return can_move_again;
    }

    @Override
    public String getName() {
        return "Velociraptor";
    }

    @Override
    public char getChar() {
        return 'V';
    }
    
    @Override
    public void damageDart() {
        System.out.println("Velociraptor é muito agil e desvia do dardo!");
    }
}
