/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafesim;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class Landlord implements Runnable {
    
    private Cafe cafe;
    private Customer cust;
    private Ingredients ing;
    private Cup cup;
    private Glass glass;
    private Assistant ast;
    public final static String LORD_IDENTITY = "Landlord";
    private Cupboard cb;
    private fountainTap ft;
    private int receiveCup;
    private long make_time = Config.makeCappu;
    
    private List<Customer> entryRecord;
    private List<Long> custTime;
   
    private boolean cappucino;
    
    private boolean closingTime = false;
    
    //initialize all values in landlord class
    public Landlord(Cafe cafe, Cupboard cb, fountainTap ft, Customer cust, Ingredients ing, Cup cup, Glass glass, Assistant ast){
        this.cafe = cafe;
        this.cb = cb;
        this.ft = ft;
        this.cust = cust;
        this.ing = ing;
        this.cup = cup;
        this.glass = glass;
        this.ast = ast;
        
        entryRecord = new ArrayList<>();
        custTime = new ArrayList<>();
    }

    //landlord run method
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Landlord.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(CColor.CYAN + "Landlord is ready to serve");
        while(!closingTime) {
           entryRecord = cafe.customersEnter();
           cust = cafe.serveArea();
           if(closingTime){
               break;
           }
           cust.orders(LORD_IDENTITY);
           takeOrders(cust.getDrink());
           for(int i =0; i<entryRecord.size(); i++){
               if(entryRecord.get(i).leaving()){
                   entryRecord.remove(i);
               }
           }

        }
        
        if(closingTime){
            while(!entryRecord.isEmpty()){
                for (int i = 0; i < entryRecord.size(); i++) {
                    if (entryRecord.get(i).leaving()) {
                        entryRecord.remove(i);
                    }
                }
            }
            cafe.waitForClosing();
            System.out.println(CColor.CYAN +"Landlord: Checking cups and glasses. Cups: " + cup.getQuantity() 
                    + " Glass: " + glass.getQuantity());
            if(!cafe.tablesEmpty()){
                System.out.println(CColor.CYAN +"Landlord: Tables are all empty");
            }
            custTime = cafe.waitingTime();
            if(!custTime.isEmpty()){
                Object obj = Collections.max(custTime);
                Object obj2 = Collections.min(custTime);
                System.out.println(CColor.CYAN + "Landlord: Maximum waiting time for customer is " + obj);
                System.out.println(CColor.CYAN + "Landlord: Minimum waiting time for customer is " + obj2);
                System.out.println(CColor.CYAN + "Landlord: Average waiting time for customer is " + averageWait());
            }
            else{
                System.out.println(CColor.CYAN +"Landlord: No waiting time since no customers");
            }
            System.out.println(CColor.CYAN +"Landlord: Served " + cafe.custServed() + " customers today!");
            System.out.println(CColor.CYAN + "Landlord is leaving now");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Landlord.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //take customer orders and give drink
    public void takeOrders(int orders){
        if(orders == 1) {
            cust.served();
            do{
                cb.waitCup(LORD_IDENTITY);
                cb.lordWait(LORD_IDENTITY);
                goToCupboard(cb, orders);
            } while(!cb.lordFlag());
            
            
            receiveCup = cb.getCup();
            makeCoffee(cb, receiveCup, ing);
            System.out.println(cust.getName() + " received cappucino from " + LORD_IDENTITY);
            cust.drinkServed();
        }
        else {
            cust.served();
            do{
                cb.waitGlass(LORD_IDENTITY);
                goToCupboard(cb, orders);
            } while(!cb.lordFlag());
            
            makeJuice(ft);
            System.out.println(cust.getName() + " received fruit juice " + LORD_IDENTITY);
            cust.drinkServed();
        }
    }
    
    //go to cupboard
    public void goToCupboard(Cupboard cupboard, int orders) {
        cupboard.lordOpen(orders);
        cupboard.lordClose();
    }
    
    //make fruit juice
    public void makeJuice(fountainTap ft){
        ft.lordUse();
        ft.lordExit();
    }
    
    //make cappucino
    public void makeCoffee(Cupboard cupboard, int cup, Ingredients ing){
        if(cupboard.getMilk() && cupboard.getCoffee()){
            try {
                Thread.sleep(make_time);
                System.out.println(CColor.CYAN + "Landlord is preparing cappucino");
                ing.fillCup(receiveCup);
                cappucino = ing.getCappucino();
            } catch (InterruptedException ex) {
                Logger.getLogger(Landlord.class.getName()).log(Level.SEVERE, null, ex);
            }
            cupboard.lordReturn(cappucino);
            cupboard.lordClose();
        }
    }
    
    //signal to customers that last orders has arrive
    public synchronized void setLastOrders(){
        System.out.println(CColor.CYAN + "Landlord: Last call for orders!!");
        for(int i =0; i<entryRecord.size(); i++){
            entryRecord.get(i).setLastOrders();
        }
    }
    
    //signal to customers that closing time is now
    public  synchronized void setClosingTime(){
        closingTime = true;
        System.out.println(CColor.CYAN + "Landlord: We are closing now!");
        for(int i =0; i<entryRecord.size(); i++){
            entryRecord.get(i).setClosingTime();
        }
    }
    
    //return average wait time of customer for drink
    public long averageWait(){
        long sum = 0;
        for(int i = 0; i < custTime.size(); i++){
            sum += custTime.get(i);
        }
        return sum/custTime.size();
    }
    
    
    
}
