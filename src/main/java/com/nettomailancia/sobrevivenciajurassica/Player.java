/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

import java.util.Random;

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
        medkits = 0;
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

    public int getPerception() {
        return perception;
    }

    public void setPerception(int perception) {
        this.perception = perception;
    }

    public void attack(Dinosaur enemy, int choice) {
        Random d6 = new Random();
        int result = d6.nextInt(6);

        switch (choice) {
            case 1:
                if (enemy instanceof TRex) {
                    System.out.println("T-rex é imune a mão");
                } else {
                    System.out.println("Atacando com a mão");
                    if (result == 6) {
                        //enemy.damage(2);
                        System.out.println("Acerto critico!");
                    } else if (result < 3) {
                        System.out.println("Erro crítico!");
                    } else {
                        System.out.println("Acerto");
                        //enemy.damage(1);
                    }
                }
                break;
            case 2:
                if (shockBat) {
                    System.out.println("Atacando com o bastão");
                    if (result >= 5) {
                        //enemy.damage(2);
                        System.out.println("Acerto critico!");
                    } else if (result == 1) {
                        System.out.println("Erro crítico!");
                    } else {
                        System.out.println("Acerto");
                        //enemy.damage(1);
                    }
                }
                break;
            case 3:
                if (darts > 0) {
                    System.out.println("Atacando com a arma de dardos");
                    darts--;
                    if (enemy instanceof Velociraptor) {
                        System.out.println("Raptor desvia!");
                    } else {
                        //enemy.damage(2);
                    }
                }
                break;
        }
    }

    public Boolean dodge() {
        if (Rng.getInstance().dice(3) <= this.getPerception()) {
            return true;
        } else {
            return false;
        }
    }

    public void heal() {
        setHp(Math.max(getHp() + 1, MAX_HP));
    }
}
