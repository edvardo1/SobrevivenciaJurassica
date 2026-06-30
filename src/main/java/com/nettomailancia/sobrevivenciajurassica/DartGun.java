/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author joaop
 */
public class DartGun extends Supply {

    @Override
    public boolean onPlayerMovement(Game game, Player p, Position tilePosition, FreeTile myTile) {
        p.addDarts(5);
        myTile.setSupply(null);
        return true;
    }
}
