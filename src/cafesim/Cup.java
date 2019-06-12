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
public class Cup {
    private final String identity = "Cup";
    private final String beverage = "Cappucino";
    private int quantity;
    private boolean noCup = false;
    private int getCup;
    
//initialize values for glass class
    public Cup() {
        quantity = Config.nCups;
    }
    
    //return identity of glass
    public String getIdentity() {
        return identity;
    }
    //return drink in glass
    public String getBeverage() {
        return beverage;
    }
    //obtain glass method
    public int checkCup(String person) {
        getCup = 0;
        if(quantity == 0){

            noCup = true;
        }
        else{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cup.class.getName()).log(Level.SEVERE, null, ex);
            }
            --quantity;
            System.out.println(person + " obtain cups. Number of cups left: " + quantity);
            ++getCup;
        }
        return getCup;
    }
    //return no glass boolean to check whether there is glass
    public boolean status(){
        return noCup;
    }
    //set quantity of glass
    public void setQuantity(int quantity){
        this.quantity += quantity;
    }
    //set status of glass presence
    public void setStatus(){
        noCup = false;
    }
    //return quantity of glass
    public int getQuantity(){
        return quantity;
    }
    
}
