/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author ebm
 */
public class Wall extends Tile {
    @Override
    boolean isOccupied() {
        return true;
    }

    @Override
    char getChar() {
        return '#';
    }
}
