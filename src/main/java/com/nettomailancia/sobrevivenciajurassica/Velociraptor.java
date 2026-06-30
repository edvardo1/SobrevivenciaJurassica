/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author joaop
 */
public class Velociraptor extends Dinosaur {

    Velociraptor() {
        this.setHp(2);
    }

    @Override
    public String getName() {
        return "Velociraptor";
    }

    @Override
    public char getChar() {
        return 'T';
    }
}
