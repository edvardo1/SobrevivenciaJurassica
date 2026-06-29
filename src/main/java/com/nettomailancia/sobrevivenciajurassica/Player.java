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
    private Boolean shockBat;
    
   public void Player(int X, int Y, int difficulty){
        
        hp = 5;
        posX = X;
        posY = Y;
        perception = difficulty;
        medkits = 0;
        darts = 0;  
        shockBat = false;
    }
    
    public void move(){
        System.out.println("Qual posição de destino? ");
        Scanner scanner = new Scanner(System.in);
        String tile = scanner.next();
        System.out.println("Tile escolhido"+tile);
        //continuar pra realmente mover no mapa
    }
    void damage(int d){
        this.hp -= d;
    }
    public void attack(Dinosaur enemy, int choice){
        Random d6 = new Random();
        int result = d6.nextInt(6);
        
        switch(choice){
            case 1:
                if (enemy instanceof TRex) {
                    System.out.println("T-rex é imune a mão");
                }else{
                    System.out.println("Atacando com a mão");
                    if(result == 6){
                        enemy.damage(2);
                        System.out.println("Acerto critico!");
                    }else if(result < 3){
                        System.out.println("Erro crítico!");
                    }else{
                        System.out.println("Acerto");
                        enemy.damage(1);
                    }
                }
            break;
            case 2:
                if(shockBat){
                    System.out.println("Atacando com o bastão");
                    if(result >= 5){
                        enemy.damage(2);
                        System.out.println("Acerto critico!");
                    }else if(result == 1){
                        System.out.println("Erro crítico!");
                    }else{
                        System.out.println("Acerto");
                        enemy.damage(1);
                    }
                }
            break;
            case 3:
                if(darts > 0){
                    System.out.println("Atacando com a arma de dardos");
                    darts--;
                    if(enemy instanceof Velociraptor){
                        System.out.println("Raptor desvia!");
                    }else{
                        enemy.damage(2);
                    }
                }
            break;
        }
    }
    public Boolean dodge(){
        Random d3 = new Random();
        int result = d3.nextInt(3);
        if(result <= this.perception){
            return true;
        }else{
            damage(1);
            return false;
        }
    }
    public void heal(){
        hp = 5;
    }
    
  
}
