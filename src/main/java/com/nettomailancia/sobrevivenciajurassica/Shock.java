/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author joaop
 */
public class Shock extends Supply {

    Shock() {
    }

    @Override
    public boolean onPlayerMovement(Game game, Player p, Position tilePosition, FreeTile myTile) {
        System.out.println("Voce adquiriu um bastao de choque!");
        p.acquireShock();
        myTile.setSupply(null);
        return true;
    }
}
