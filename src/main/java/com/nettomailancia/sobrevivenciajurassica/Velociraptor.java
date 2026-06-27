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
    
    void Velociraptor (){
        hp = 2;
    }
    void move (){
        
    }
    void damage(int d){
        this.hp -= d;
    }
    
}
