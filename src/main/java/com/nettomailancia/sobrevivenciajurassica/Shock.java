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
    public boolean onPlayerMovement(Player p, FreeTile myTile) {
        p.acquireShock();
        myTile.setSupply(null);
        return true;
    }
}
