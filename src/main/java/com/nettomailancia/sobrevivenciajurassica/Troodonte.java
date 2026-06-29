/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author joaop
 */
public class Troodonte extends Dinosaur {
    Troodonte() {
        hp = 2;
    }
    public char getChar() {
        return 'T';
    }
    void move (){
        
    }
    void damage(int d){
        this.hp -= d;
    }
}
