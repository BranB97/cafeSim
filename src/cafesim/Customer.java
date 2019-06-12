/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafesim;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class Customer implements Runnable {

    private String name;
    private int beverage;
    private int noDrinks = Config.nDrinks;
    private int count;
    private long orderTime;
    private long waitingTime;
    private long drink_time = Config.drinkTime;
    private boolean flag;
    private boolean juiceflag;
    private boolean orderflag;
    private boolean getDrink;
    private boolean status;
    private boolean satisfied = false;
    private boolean leaving = false;
    private boolean lastOrders = false;
    private boolean closingTime = false;
    
    private Date time;
    private Cafe cafe;
    
    //initialize values for customer class
    public Customer(Cafe cafe){
        this.cafe = cafe;
    }
    
    //getter for customer name
    public String getName() {
        return name;
    }
 
    //getter for customer entry time
    public Date getInTime() {
        return time;
    }
 
    //setter for customer name
    public void setName(String name) {
        this.name = name;
    }
 
    //setter for customer name
    public void setInTime(Date time) {
        this.time = time;
    }
    
    //customer run method
    @Override
    public void run() {
       willOrderAgain();
       lastJuice();
       goCafe();
       waitServed();
       if(!leaving){
           waitForDrink();
           drink(beverage);
           count++;
           do {
               findTable();
               waitTable();
               goTable();
           } while (!satisfied);
           cafe.servedCounter();
           while(!closingTime && count != noDrinks){
               if(lastOrders){
                   if(orderflag || juiceflag){
                       break;
                   }
               }
               goGetDrinkAgain();
           }
           if(!leaving){
               exitCafe(new Date());
           }
       }
    }
    
    //order method for customer
    public void orders(String person) {
        double choice = Math.random();
        Date date = new Date();
        orderTime = date.getTime();
        if(choice < 0.5){
            beverage = 1;
            System.out.println(name + " orders cappuchino from " + person);
        }
        else{
            beverage = 2;
            System.out.println(name + " orders fruit juice from " + person);
        }
    }
    
    //chance to order again when landlord call last orders
    public void willOrderAgain(){
        double choice = Math.random();
        orderflag = choice < 0.5;
    }
    
    public void lastJuice(){
        double choice = Math.random();
        orderflag = choice < 0.5;
    }
    
    //return customer beverage
    public int getDrink() {
        return beverage;
    }
    
    //wait method for waiting drink
    public synchronized void waitForDrink (){
          while(!getDrink){
                try {
                    System.out.println(name + " waiting for drink");
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cafe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
    
    //notify customer that drink is served
    public synchronized void drinkServed (){
        Date date = new Date();
        waitingTime = date.getTime() - orderTime;
        cafe.addTime(waitingTime);
        getDrink = true;
        notify();
    }
    
    //wait method for waiting to be served
    public synchronized void waitServed () {
        while(!status && !closingTime){
                try {
                    System.out.println(name + " waiting to be served");
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cafe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        if(closingTime){
           exitCafe(new Date());
       }
    }
    
    //notify customer that landlord/barmaid can serve 
    public synchronized void served(){
        status = true;
        notify();
    }
    
    //drink method
    public void drink(int beverage) {
        synchronized (this) {
            if (getDrink) {
                if (beverage == 1) {
                    System.out.println(name + " is drinking cappucino");
                } else {
                    System.out.println(name + " is drinking fruit juice");
                }
            }
        }
         try {
             if(!lastOrders || beverage != 1 || juiceflag != true){
                 Thread.sleep(drink_time);
                 System.out.println(name + " finishes drinking");
             }
             else{
                 System.out.println(name + " instantly finishes juice to order one last drink");
             }
            } catch (InterruptedException ex) {
                Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    //wait for table if no empty ones
    public synchronized void waitTable(){
        while(!flag){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //notiify customer that tables are cleared
    public synchronized void tablesCleared(){
        flag = true;
        notifyAll();
    }
    
    //enter cafe
    public void goCafe() {
        cafe.add(this);
    }
    
    //find a table
    public void findTable(){
        flag = cafe.tableStatus(this);
    }
    
    //put down cup on table
    public void goTable(){
        cafe.searchTables(this);
    }
    
    //for landlord to set last orders for customers as signal
    public synchronized void setLastOrders(){
        lastOrders = true;
    }
    
    //for landlord to set closing time for customers as signal
    public synchronized void setClosingTime(){
        closingTime = true;
        status = true;
        notifyAll();
    }
    
    //leave cafe
    public synchronized void exitCafe(Date time){
        System.out.println(name+ " is leaving the cafe now at " + time);
        System.out.println(name+": goodbye landlord");
        leaving = true;
    }
    
    //set true after putting down drink
    public void satisfied(){
        satisfied = true;
    }
    
    //return leaving boolean for landlord to delete
    public boolean leaving(){
        return leaving;
    }
    
    //get another drink method
    public void goGetDrinkAgain(){
        flag = false;
        getDrink = false;
        status = false;
        satisfied = false;
        cafe.goBack(this);
        waitServed();
         if(!leaving){
           waitForDrink();
           drink(beverage);
           count++;
           do {
               findTable();
               waitTable();
               goTable();
           } while (!satisfied);
         }
    }
    
    
    
}
