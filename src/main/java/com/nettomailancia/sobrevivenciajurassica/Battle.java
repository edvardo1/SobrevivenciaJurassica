/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

import java.util.ArrayList;

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
    
    private GameWindow battleWindow;
    public ArrayList<String> battleLog = new ArrayList<>();
    private String playernum1Action;
    private String dinoAction;
    

    public Battle(Game g, Player p, Dinosaur f, boolean ambush) {
        game = g;
        player = p;
        foe = f;
        isAmbush = ambush;
        addBattleMessage("Inicia-se uma batalha!");
        
        // instancia e abre uma gamewindow de batalha
    }

    public boolean isTryingToRunAway() {
        return tryingToRunAway;
    }

    public Dinosaur getFoe() {
        return foe;
    }

    public String getDinoAction() {
        return dinoAction;
    }
    
    public void setFoe(Dinosaur foe) {
        this.foe = foe;
    }
    
    public String getPlayerAction() {
        return playernum1Action;
    }

    public boolean isOver() {
        return player.getHp() <= 0 || foe.getHp() <= 0;
    }

    public void playerInput(char key) {
        tryingToRunAway = false;
        switch (key) {
            case 'r':
                addBattleMessage("Você tentou fugir!");
                tryingToRunAway = true;
                return;
            case 'p':
                if (player.hasShock()) {
                    foe.damageShockBaton();
                    addBattleMessage("Você atacou com o bastão de choque!");
                    playernum1Action = "shock";
                } else {
                    foe.damageHand();
                    addBattleMessage("Você atacou com os punhos!");
                    playernum1Action = "punch";
                }
                break;
            case 'd':
                if (player.hasDarts()) {
                    foe.damageDart();
                    player.loseDarts(1);
                    addBattleMessage("Você lançou um dardo!");
                    playernum1Action = "dart";
                } else {
                    addBattleMessage("Você não tem dardos!");
                    return;
                }
                break;
            default:
                return;
        }
        foeTurn();
        if (foe.getHp() <= 0) {
            addBattleMessage("O " + foe.getName() + " morreu!");
        }
    }

    private void foeTurn() {
        if (foe.getHp() <= 0) {
            return;
        }

        if (Rng.getInstance().dice(3) <= player.getPerception()) {
            foe.attackPlayer(player);
            addBattleMessage("O " + getFoe().getName() + " te ataca!");
            dinoAction = "bite";
        }
    }

    public void beginBattle() {
        if (isAmbush) {
            foeTurn();
            isAmbush = false;
        }
    }
    
    public void addBattleMessage(String message) {
        battleLog.add(message);
        while (battleLog.size() > 4) {
            battleLog.remove(0);
        }
    }
    
}
