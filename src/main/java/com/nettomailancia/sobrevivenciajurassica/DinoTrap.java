/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author ebm
 */
public class DinoTrap extends Supply {

    Dinosaur dino;

    DinoTrap(Dinosaur d) {
        dino = d;
    }

    @Override
    public boolean onPlayerMovement(Player p, FreeTile myTile) {
        myTile.setSupply(null);
        myTile.setEntity(dino);
        return false;
    }
}
