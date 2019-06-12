/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafesim;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class Assistant implements Runnable {
    private Cafe cafe;
    private Customer cust;
    private List<Customer> notifyCustomers;
    
    public final static String ASSISTANT_IDENTITY = "Assistant";
    private Cupboard cb;
    
    private boolean closingTime = false;
    private long time_wash = Config.washTime;
    private long time_rest = Config.restTime;
    private int cups;
    private int glasses;
    
    //initialize values for assitant class
    public Assistant(Cafe cafe, Cupboard cb, Customer cust){
        this.cafe = cafe;
        this.cb = cb;
        this.cust = cust;
        notifyCustomers = new ArrayList<Customer>();
    }
    
    //run method for assitant
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Landlord.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(CColor.YELLOW + "Assistant is ready to serve");
        while(!closingTime || cafe.tablesEmpty()) {
            notifyCustomers = cafe.notifyCust();
            cafe.clearTables(this);
            for(int j = 0; j < notifyCustomers.size(); j++){
                notifyCustomers.get(j).tablesCleared();
                notifyCustomers.remove(j);
            }
            if(cups != 0 || glasses != 0){
                washing();
                goToCupboard();
                resting();
            }
        }
        
        if(closingTime){
            while(cafe.tablesEmpty() || !notifyCustomers.isEmpty() || !cafe.customersEnter().isEmpty()){
                cafe.clearTables(this);
                for (int j = 0; j < notifyCustomers.size(); j++) {
                    notifyCustomers.get(j).tablesCleared();
                    notifyCustomers.remove(j);
                }
                if (cups != 0 || glasses != 0) {
                    washing();
                    goToCupboard();
                    resting();
                }
            }
            System.out.println(CColor.YELLOW + "Assistant: Im leaving now");
            cafe.setAstLeft();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Landlord.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //collect cups and glass from table
    public void collect(int cups, int glasses){
        this.cups += cups;
        this.glasses += glasses;
    }
    
    //wash cups and glass
    public void washing(){
        try {
            System.out.println(CColor.YELLOW + "Assistant is washing " + cups + " cups and " + glasses + " glasses");
            Thread.sleep(time_wash);
        } catch (InterruptedException ex) {
            Logger.getLogger(Assistant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //return cups to cupboard
    public int getCups(){
        return cups;
    }
    
    //return glass to cupboard
    public int getGlass(){
        return glasses;
    }
    
    //set cup to zero
    public void resetCup(){
        cups = 0;
    }
    
    //set glass to zero
    public void resetGlass(){
        glasses = 0;
    }
    
    //go cupboard
    public void goToCupboard(){
        cb.astOpen(this);
        cb.astClose();
    }
    
    //rest time
    public void resting(){
        try {
            System.out.println(CColor.YELLOW + ASSISTANT_IDENTITY + " is now resting for awhile");
            Thread.sleep(time_rest);
        } catch (InterruptedException ex) {
            Logger.getLogger(Assistant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //notify assistant that its closing time
    public  synchronized void setClosingTime(){
        closingTime = true;
        System.out.println(CColor.YELLOW + "Assistant: We are closing now!");
    }
    
}
