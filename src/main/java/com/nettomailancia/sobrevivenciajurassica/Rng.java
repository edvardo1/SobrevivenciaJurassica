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
    private long seed;

    private static Rng instance;

    private Rng() {
        setSeed(System.currentTimeMillis());
    }

    public static Rng getInstance() {
        if (instance == null) {
            instance = new Rng();
        }
        return instance;
    }

    public void randomize() {
        setSeed(System.currentTimeMillis());
    }

    public void setSeed(long seed) {
        this.seed = seed;
        random = new Random(seed);
    }

    public long getSeed() {
        return seed;
    }

    public int dice(int sides) {
        return 1 + random.nextInt(sides);
    }

    public int nextInt(int i) {
        return random.nextInt(i);
    }
}