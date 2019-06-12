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
public class Ingredients {
    private boolean getMilk = false;
    private boolean getCoffee = false;
    private boolean cappucinoFinish = false;
    private boolean lordTaken;
    private boolean maidTaken;
    
    private long fetch_time = Config.fetchIng;
    
    //initiliaze values in ingredients class
    Ingredients(){
        fetch_time = Config.fetchIng;
    }
    
    //landlord gather method
    public void lordGather(){
        synchronized(this){
            lordTaken = true;
        }
        try {
            Thread.sleep(fetch_time);
            synchronized(this){
                System.out.println(CColor.CYAN + "Landlord is fetching the milk");
                fetchMilk();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(fountainTap.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Thread.sleep(fetch_time);
            synchronized(this){
                System.out.println(CColor.CYAN + "Landlord is fetching the coffee");
                fetchCoffee();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(fountainTap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //maid gather method
    public void maidGather(){
        synchronized(this){
            maidTaken = true;
        }
        try {
            Thread.sleep(fetch_time);
            synchronized(this){
                System.out.println(CColor.PURPLE + "Barmaid is fetching the milk");
                fetchMilk();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(fountainTap.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Thread.sleep(fetch_time);
            synchronized(this){
                System.out.println(CColor.PURPLE +"Barmaid is fetching the coffee");
                fetchCoffee();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(fountainTap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //landlord return method
    public void lordReturn(){
        synchronized(this){
            System.out.println(CColor.CYAN +"Landlord is done with the coffee and milk");
            lordTaken = false;
            returnMilk();
            returnCoffee();
        }

    }
    
    //barmaid return method
    public void maidReturn(){
        synchronized(this){
            System.out.println(CColor.PURPLE +"Barmaid is done with the coffee and milk");
            maidTaken = false;
            returnMilk();
            returnCoffee();
        }
    }
    
    //fetch milk
    public void fetchMilk() {
        getMilk = true;
    }
    
    //fetch coffee
    public void fetchCoffee(){
        getCoffee = true;
    }
    
    //mix milk
    public boolean mixMilk(){
        return getMilk;
    }
    
    //mix coffee
    public boolean mixCoffee() {
        return getCoffee;
    }
    
    //return milk
    public void returnMilk(){
        getMilk = false;
    }
    
    //return coffee
    public void returnCoffee(){
        getCoffee = false;
    }
    
    //fill cup with cappucino
    public void fillCup(int cup){
        cappucinoFinish = true;
    }
    
    //return finish cappucino
    public boolean getCappucino() {
        return cappucinoFinish;
    }
    
    //return a boolean on whether landlord has taken ingredient
    public boolean lordTaken() {
        return lordTaken;
    }
    
    //return a boolean on whether barmaid has taken ingredient
    public boolean maidTaken() {
        return maidTaken;
    }
    
}
