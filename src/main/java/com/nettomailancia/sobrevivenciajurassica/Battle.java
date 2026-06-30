/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nettomailancia.sobrevivenciajurassica;
import java.util.Scanner;
/**
 *
 * @author joaop
 */
public class Battle {
    private Player number1;
    private Dinosaur foe;
    Scanner scanner = new Scanner(System.in);
    
     
    public void Battle (Player n1, Dinosaur f){
        number1 = n1;
        foe = f;
    }
    public Player getPlayer(){
        return number1;
    }
    public Dinosaur getDinosaur(){
        return foe;
    }
    public void runBattle(){
        System.out.println("Atacar com:\n 1. A mão\n 2. Bastão\n 3. Arma de dardos");
        int choice = scanner.nextInt();
        number1.attack(foe, choice);
        if(foe.getHp() > 0){
            number1.dodge();
        }
    }
}

