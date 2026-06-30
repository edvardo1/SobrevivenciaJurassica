/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author joaop
 */
public class MedKit extends Supply {

    MedKit() {
    }

    @Override
    public boolean onPlayerMovement(Game game, Player p, Position tilePosition, FreeTile myTile) {
        p.setHp(p.getHp() + 1);
        myTile.setSupply(null);
        return true;
    }
}
