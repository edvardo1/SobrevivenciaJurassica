/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

import java.util.Random;

/**
 *
 * @author ebm
 */
public class Rng {

    private Random random;
    private static Rng instance;

    private Rng() {
        random = new Random();
    }

    public static Rng getInstance() {
        if (instance == null) {
            instance = new Rng();
        }
        return instance;
    }

    public int dice(int sides) {
        return 1 + random.nextInt(sides);
    }
    
    public int nextInt(int i) {
        return random.nextInt(i);
    }
}
