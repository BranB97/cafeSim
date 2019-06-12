/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafesim;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class CafeTable {
    private int identity;
    private final int max = Config.capacity;
    private int capacity[] = new int[max];
    private long time_pickup = Config.pickUp;
    private int items;
    private int cups;
    private int glass;
    private int flag;
    
    //initialize values in cafe table class
    public CafeTable(int identity){
        items = -1;
        this.identity = identity;
    }
    
    //check whether table is full
    public int isFull() {
        int full = 0;
        
        if(items == max - 1){
            full = 1;
        }
        return full;
    }
    
    //check whether table is empty
    public int isEmpty() {
        int empty = 0;
        
        if(items == -1){
            empty = 1;
        }
        return empty;
    }
    
    //place cups/glasses on table
    public void putOnTable(int orders, String name){
        try {
            flag = 0;
            Thread.sleep(2500);
            if(orders == 1){
                if(items == max - 2){
                    flag = 1;
                    System.out.println("No space to put cup for " + name);
                }
                else{
                    cups++;
                    items += 2;
                    capacity[items] = cups;
                    System.out.println(name + " put down cappucino on " + identity 
                            + " after finish drinking.Cups: " + cups+ " Glass: " + glass);
                }
            }
            else{
                glass++;
                items++;
                capacity[items] = glass;
                System.out.println(name + " put down fruit juice on " + identity 
                        + " after finish drinking.Cups: " + cups+ " Glass: " + glass);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(CafeTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //remove cups/glass from table
    public void removeFromTable(){
        try {
            Thread.sleep(2500);
            while(isEmpty()== 0){
                items--;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(CafeTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //obtain cups
    public int retrieveCups() {
        try {
            Thread.sleep(time_pickup);
        } catch (InterruptedException ex) {
            Logger.getLogger(CafeTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cups;
    }
    
    //obtain glass
    public int retrieveGlass(){
        try {
            Thread.sleep(time_pickup);
        } catch (InterruptedException ex) {
            Logger.getLogger(CafeTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return glass;
    }
    
    //set number of cups to zero
    public void resetCups(){
        cups = 0;
    }
    
    //set number of glass to zero
    public void resetGlass(){
        glass = 0;
    }
    //return identity of table
    public int getIdentity(){
        return identity;
    }
    
    //return flag for checking whether cup can still be placed on table
    public int getFlag(){
        return flag;
    }
    
    //notify all customers
    public synchronized void clearTable(){
        notifyAll();
    }
    
}
