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
public class Glass {
    private final String identity = "Glass";
    private final String beverage = "Fruit juice";
    private int quantity;
    private boolean noGlass = false;
    private int getGlass;
    
    //initialize values for glass class
    public Glass() {
        quantity = Config.nGlasses;
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
    public int checkGlass(String person) {
        getGlass = 0;
        if(quantity == 0){

            noGlass = true;
        }
        else{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Glass.class.getName()).log(Level.SEVERE, null, ex);
            }
            --quantity;
            System.out.println(person + " obtain glass. Number of glass left: " + quantity);
            ++getGlass;
        }
        return getGlass;
    }
    
    //return no glass boolean to check whether there is glass
    public boolean status(){
        return noGlass;
    }
    
    //set quantity of glass
    public void setQuantity(int quantity){
        this.quantity += quantity;
    }
    
    //set status of glass presence
    public void setStatus(){
        noGlass = false;
    }
    
    //return quantity of glass
    public int getQuantity(){
        return quantity;
    }
    
}
