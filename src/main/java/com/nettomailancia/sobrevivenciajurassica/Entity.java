/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;

/**
 *
 * @author joaop
 */
abstract public class Entity {
    int hp;
    
    void Entity (  ){}
    //char getChar{}
    abstract void move ();
    abstract void damage ( int d );
    int getHp(){
        return hp;
    }
    void setHp(int life){
        this.hp = life;
    }
    
}
