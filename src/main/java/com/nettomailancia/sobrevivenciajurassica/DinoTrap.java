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
    public boolean onPlayerMovement(Game game, Player player, Position tilePosition, FreeTile myTile) {
        System.out.println("A caixa era uma armadilha!");
        myTile.setSupply(null);
        myTile.setEntity(dino);
        dino.setPosition(tilePosition);
        game.setBattle(new Battle(player, dino, true));
        return false;
    }
}
