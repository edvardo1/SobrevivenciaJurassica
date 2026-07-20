/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author joaop
 */
public class Player extends Entity {

    static final int MAX_HP = 5;
    private int perception;
    private int medkits;
    private int darts;
    private boolean shockBat;

    Player(int difficulty) {
        setHp(MAX_HP);
        perception = difficulty;
        medkits = 1;
        darts = 0;
        shockBat = false;
    }

    @Override
    public char getChar() {
        return '@';
    }

    public void addDarts(int amount) {
        darts += amount;
    }

    public void loseDarts(int amount) {
        darts -= amount;
    }

    public void acquireShock() {
        shockBat = true;
    }

    public int getDarts() {
        return darts;
    }

    public boolean hasDarts() {
        return darts > 0;
    }

    public boolean hasShock() {
        return shockBat;
    }

    public int getMedkits() {
        return medkits;
    }

    public int getPerception() {
        return perception;
    }

    public void setPerception(int perception) {
        this.perception = perception;
    }

    public Boolean dodge() {
        if (Rng.getInstance().dice(3) <= this.getPerception()) {
            return true;
        } else {
            return false;
        }
    }

    public void tryHeal() {
        setHp(Math.max(getHp() + 1, MAX_HP));
        medkits -= 1;
    }
}
