/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author joaop
 */
public class Player extends Entity {

    private int posX;
    private int posY;
    private int perception;
    private int medkits;
    private int darts;
    private boolean shockBat;

    Player(int x, int y, int difficulty) {
        hp = 5;
        posX = x;
        posY = y;
        perception = difficulty;
        medkits = 0;
        darts = 0;
        shockBat = false;
    }

    @Override
    public char getChar() {
        return '@';
    }

    @Override
    public boolean move(Direction d, TileMap tm) {
        try {
            FreeTile currentTile = (FreeTile) tm.getTile(posX, posY);
            int newX = posX;
            int newY = posY;
            if (null != d) {
                switch (d) {
                    case NORTH:
                        newY -= 1;
                        break;
                    case WEST:
                        newX -= 1;
                        break;
                    case SOUTH:
                        newY += 1;
                        break;
                    case EAST:
                        newX += 1;
                        break;
                    default:
                        break;
                }
            }
            boolean inBoundsX = 0 <= newX && newX <= tm.getWidth();
            boolean inBoundsY = 0 <= newY && newY <= tm.getHeight();
            if (!inBoundsX || !inBoundsY) {
                return false;
            }
            Tile nextTile = tm.getTile(newX, newY);
            if (nextTile == null) {
                throw new Exception("what");
            }
            if (nextTile instanceof Wall) {
                return false;
            }
            FreeTile nextFreeTile = (FreeTile) nextTile;
            if (nextFreeTile.isOccupied()) {
                return false;
            }

            posX = newX;
            posY = newY;
            currentTile.setEntity(null);
            nextFreeTile.setEntity(this);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    void damage(int d) {
        this.hp -= d;
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
                        enemy.damage(2);
                        System.out.println("Acerto critico!");
                    } else if (result < 3) {
                        System.out.println("Erro crítico!");
                    } else {
                        System.out.println("Acerto");
                        enemy.damage(1);
                    }
                }
                break;
            case 2:
                if (shockBat) {
                    System.out.println("Atacando com o bastão");
                    if (result >= 5) {
                        enemy.damage(2);
                        System.out.println("Acerto critico!");
                    } else if (result == 1) {
                        System.out.println("Erro crítico!");
                    } else {
                        System.out.println("Acerto");
                        enemy.damage(1);
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
                        enemy.damage(2);
                    }
                }
                break;
        }
    }

    public Boolean dodge() {
        Random d3 = new Random();
        int result = d3.nextInt(3);
        if (result <= this.perception) {
            System.out.println("O dinosauro erra!");
            return true;
        } else {
            System.out.println("O dinosauro acerta um golpe!");
            damage(1);
            return false;
        }
    }

    public void heal() {
        hp = 5;
    }
}
