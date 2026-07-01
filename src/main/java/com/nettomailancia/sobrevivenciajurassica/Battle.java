/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

import java.util.Scanner;

/**
 *
 * @author joaop
 */
public class Battle {

    private Player player;
    private Dinosaur foe;
    boolean isAmbush;

    public Battle(Player p, Dinosaur f, boolean ambush) {
        player = p;
        foe = f;
        isAmbush = ambush;
    }

    private void playerTurn() {
        Scanner terminalInput = new Scanner(System.in);
        boolean validOption = true;
        do {
            validOption = true;
            System.out.println("Opcoes:");
            System.out.println("r. Fugir");
            if (player.hasShock()) {
                System.out.println("p. Lutar com bastao de choque");
            } else {
                System.out.println("p. Lutar com punhos");
            }
            if (player.hasDarts()) {
                System.out.println("d. Atirar dardo");
            }
            String s = terminalInput.nextLine();

            if (s.equals("r")) {
                /* run away */
            } else if (s.equals("p")) {
                if (player.hasShock()) {
                    getFoe().damageShockBaton();
                } else {
                    getFoe().damageHand();
                }
                System.out.println("Voce atacou o " + getFoe().getName() + "!");
            } else if (player.hasDarts() && s.equals("d")) {
                getFoe().damageDart();
                player.loseDarts(1);
            } else {
                validOption = false;
                System.out.println("Opcao invalida");
            }
        } while (!validOption);
    }

    private void foeTurn() {
        if (getFoe().getHp() > 0) {
            if (Rng.getInstance().dice(3) <= player.getPerception()) {
                System.out.println("O " + getFoe().getName() + " te ataca!");
                getFoe().attackPlayer(player);
            }
        }
    }

    public void turn() {
        if (isAmbush) {
            System.out.println("Algo te surpreende!!");
            foeTurn();
            isAmbush = false;
        } else {
            playerTurn();
            foeTurn();
        }
        if (getFoe().getHp() <= 0) {
            System.out.println("O " + getFoe().getName() + " esta morto.");
        }
    }

    public boolean isOver() {
        return player.getHp() <= 0 || getFoe().getHp() <= 0;
    }

    public Dinosaur getFoe() {
        return foe;
    }

    public void setFoe(Dinosaur foe) {
        this.foe = foe;
    }
}
