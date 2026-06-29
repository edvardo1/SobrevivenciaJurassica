/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author joaop
 */
public class Compsognathus extends Dinosaur {
    Compsognathus() {
        hp = 1;
    }
    char getChar() {
        return 'C';
    }
    void move (){
        
    }
    void damage(int d){
        this.hp -= d;
    }
}
