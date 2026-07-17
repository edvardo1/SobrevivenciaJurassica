/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author joaop
 */
public class Battle {
    private final Game game;
    private Player player;
    private Dinosaur foe;
    private boolean isAmbush;
    private boolean tryingToRunAway;

    public Battle(Game g, Player p, Dinosaur f, boolean ambush) {
        game = g;
        player = p;
        foe = f;
        isAmbush = ambush;
    }

    public boolean isTryingToRunAway() {
        return tryingToRunAway;
    }

    public Dinosaur getFoe() {
        return foe;
    }

    public void setFoe(Dinosaur foe) {
        this.foe = foe;
    }

    public boolean isOver() {
        return player.getHp() <= 0 || foe.getHp() <= 0;
    }

    public void playerInput(char key) {

        tryingToRunAway = false;

        switch (key) {

            case 'r':
                tryingToRunAway = true;
                break;

            case 'p':
                if (player.hasShock()) {
                    foe.damageShockBaton();
                } else {
                    foe.damageHand();
                }
                break;

            case 'd':
                if (player.hasDarts()) {
                    foe.damageDart();
                    player.loseDarts(1);
                }
                break;

            default:
                return;
        }

        foeTurn();

        if (foe.getHp() <= 0) {
            return;
        }
    }

    private void foeTurn() {

        if (foe.getHp() <= 0) {
            return;
        }

        if (Rng.getInstance().dice(3) <= player.getPerception()) {
            foe.attackPlayer(player);
        }
    }

    public void beginBattle() {
        if (isAmbush) {
            foeTurn();
            isAmbush = false;
        }
    }
}
