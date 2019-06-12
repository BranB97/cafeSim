/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafesim;

import java.util.Scanner;

/**
 *
 * @author acer
 */
public class Config {
    static int nTables;
    static int nCups;
    static int nGlasses;
    static long fetchIng;
    static long makeCappu;
    static long makeJuice;
    static int capacity;
    static long pickUp;
    static long washTime;
    static long restTime;
    static int nCust;
    static long drinkTime;
    static int nDrinks;
    
    //prompt for all input
    public void input (){
        System.out.println(CColor.GREEN +"\t Welcome to LeBlanc Cafe configuration menu\n");
        in_noTable();
        in_noCups();
        in_noGlasses();
        in_fetchIngTime();
        in_cuppaTime();
        in_makeJuice();
        in_capacity();
        in_pickUp();
        in_washTime();
        in_restTime();
        in_nCust();
        in_drinkTime();
        in_nDrinks();
        
    }
    
    //number of table
    public void in_noTable(){
        boolean check = false;
        do{
            System.out.println("Enter number of tables: ");
            Scanner sc = new Scanner(System.in);
            nTables = sc.nextInt();
            if(nTables > 0){
                check = true;
            }
        }while(!check);
        
    }
    
    //number of cups
    public void in_noCups(){
        boolean check = false;
        do{
            System.out.println("Enter number of cups: ");
            Scanner sc = new Scanner(System.in);
            nCups = sc.nextInt();
            if(nCups > 0){
                check = true;
            }
        }while(!check);
        
    }
    
    //number of glass
    public void in_noGlasses(){
        boolean check = false;
        do{
            System.out.println("Enter number of glasses: ");
            Scanner sc = new Scanner(System.in);
            nGlasses = sc.nextInt();
            if(nGlasses > 0){
                check = true;
            }
        }while(!check);
        
    }
    
    //time to fetch cappucino ingredients
    public void in_fetchIngTime(){
        boolean check = false;
        do{
            System.out.println("Set how long to fetch cappucino ingredients: ");
            Scanner sc = new Scanner(System.in);
            fetchIng = sc.nextInt();
            if(fetchIng > 0){
                check = true;
            }
        }while(!check);
        
    }
    
    //time to make cappucino
    public void in_cuppaTime(){
        boolean check = false;
        do{
            System.out.println("Set how long to make cappucino: ");
            Scanner sc = new Scanner(System.in);
            makeCappu = sc.nextInt();
            if(makeCappu > 0){
                check = true;
            }
        }while(!check);
        
    }
    
    //time to make juice
    public void in_makeJuice(){
        boolean check = false;
        do{
            System.out.println("Set how long to make juice: ");
            Scanner sc = new Scanner(System.in);
            makeJuice = sc.nextInt();
            if(makeJuice > 0){
                check = true;
            }
        }while(!check);
        
    }
    
    //capacity of table
    public void in_capacity(){
        boolean check = false;
        do{
            System.out.println("Enter table capacity: ");
            Scanner sc = new Scanner(System.in);
            capacity = sc.nextInt();
            if(capacity >= 2){
                check = true;
            }
        }while(!check);
        
    }
    
    //time to pick up cups and glass
    public void in_pickUp(){
        boolean check = false;
        do{
            System.out.println("Set how long to pick up cups and glasses: ");
            Scanner sc = new Scanner(System.in);
            pickUp = sc.nextInt();
            if(pickUp > 0){
                check = true;
            }
        }while(!check);
        
    }
    
    //time to wash cups and glasses
    public void in_washTime(){
        boolean check = false;
        do{
            System.out.println("Set how long to wash cups and glasses: ");
            Scanner sc = new Scanner(System.in);
            washTime = sc.nextInt();
            if(washTime > 0){
                check = true;
            }
        }while(!check);
        
    }
    
    //time to rest for assistant
    public void in_restTime(){
        boolean check = false;
        do{
            System.out.println("Set how long for assistant to rest: ");
            Scanner sc = new Scanner(System.in);
            restTime = sc.nextInt();
            if(restTime > 0){
                check = true;
            }
        }while(!check);
        
    }
    
    //number of customers
    public void in_nCust(){
        boolean check = false;
        do{
            System.out.println("Enter number of customers: ");
            Scanner sc = new Scanner(System.in);
            nCust = sc.nextInt();
            if(nCust >= 0){
                check = true;
            }
        }while(!check);
        
    }
    
    //drinking time
    public void in_drinkTime(){
        boolean check = false;
        do{
            System.out.println("Set how long for customer to drink: ");
            Scanner sc = new Scanner(System.in);
            drinkTime = sc.nextInt();
            if(drinkTime > 0){
                check = true;
            }
        }while(!check);
        
    }
    
    //number of drinks customer will order
    public void in_nDrinks(){
        boolean check = false;
        do{
            System.out.println("Enter number of drinks: ");
            Scanner sc = new Scanner(System.in);
            restTime = sc.nextInt();
            if(restTime >= 0){
                check = true;
            }
        }while(!check);
        
    }
}
