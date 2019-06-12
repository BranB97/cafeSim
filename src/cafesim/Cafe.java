/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafesim;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class Cafe {
    private List<Customer> listCustomer;
    private List<Customer> waitingCustomers;
    private List<Customer> customersEnter;
    private List<CafeTable> tables;
    private List<Long> waitingTime;
    private Customer customer;
    private Customer delCustomer;
    private int custServed;
    
    private boolean closingTime = false;
    private boolean astLeft;
    private boolean maidLeft;
    
    //initialize values in cafe class
    public Cafe(){
        System.out.println(CColor.GREEN + "LeBlanc cafe is now open for business!!");
        listCustomer = new LinkedList<>();
        waitingCustomers = new ArrayList<>();
        customersEnter = new ArrayList<>();
        tables = new ArrayList<>();
        waitingTime = new ArrayList<>();
        
        for(int i = 0; i < Config.nTables; i++){
            CafeTable ct = new CafeTable(i);
            tables.add(ct);
        }
    }
    
    //serving area where customers queue
    public Customer serveArea(){
        synchronized (listCustomer)
        {
            while(listCustomer.isEmpty() && !closingTime)
            {
                System.out.println(CColor.GREEN + "There are no customers so far..");
                try
                {
                    if(!closingTime){
                        listCustomer.wait();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cafe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            customer = (Customer) ((LinkedList<?>) listCustomer).poll();
           return customer;
        }
    }
    
    //customers are added into cafe
    public void add(Customer customer) {
        System.out.println(customer.getName() + " entering the cafe at " + customer.getInTime() + "!!");
        System.out.println(customer.getName() + ": Hello Landlord!");
        customersEnter.add(customer);
        synchronized (listCustomer)
        {
            ((LinkedList<Customer>)listCustomer).offer(customer);
            System.out.println(customer.getName() + " is queueing");
            
            if(listCustomer.size() == 1)
            {
                listCustomer.notify();
            }
        }
    }
    
    //for customers to queue again for another drink
    public void goBack(Customer customer){
        synchronized (listCustomer)
        {
            ((LinkedList<Customer>)listCustomer).offer(customer);
            System.out.println(customer.getName() + " is queueing again");
            
            if(listCustomer.size() == 1)
            {
                listCustomer.notify();
            }
        }
    }
    
    //check whether table is empty
    public boolean tableStatus(Customer customer){
        boolean status = false;
        synchronized(tables){
            for(int i = 0; i < tables.size(); i++){
                if(tables.get(i).isFull() == 0){
                    if(tables.get(i).getFlag() == 1){
                        status = false;
                    }
                    else{
                        status = true;
                        System.out.println(customer.getName()+" has found a table " + tables.get(i).getIdentity());
                        break;
                    }
                }
            }
            if(!status){
                System.out.println(customer.getName() + " :No available tables..");
                waitingCustomers.add(customer);
            }
        }
        return status;
        
    }
    
    //put down cup on empty table
    public void searchTables(Customer customer){
        synchronized(tables){
            for(int i = 0; i < tables.size(); i++){
                if(tables.get(i).isFull() == 0){
                    tables.get(i).putOnTable(customer.getDrink(), customer.getName());
                     if(tables.get(i).getFlag() != 1){
                         customer.satisfied();
                         break;
                     }
                }
            }
        }
    }
    
    //clear table
    public void clearTables(Assistant ast){
        synchronized(tables){
            for (int i = 0; i < tables.size(); i++) {
                if (tables.get(i).isEmpty() == 0) {
                    tables.get(i).removeFromTable();
                    System.out.println(CColor.YELLOW + "Assistant: Collected " + tables.get(i).retrieveCups() 
                            + " cups and " + tables.get(i).retrieveGlass() + " glasses from table " 
                            + tables.get(i).getIdentity());
                    ast.collect(tables.get(i).retrieveCups(), tables.get(i).retrieveGlass());
                    tables.get(i).resetCups();
                    tables.get(i).resetGlass();
                    
                }
            }
            
        }
    }
    
    //check whether table is empty for assitant and landlord
    public boolean tablesEmpty(){
        synchronized(tables){
            for (int i = 0; i < tables.size(); i++) {
                if (tables.get(i).isEmpty() == 0) {
                    return true;
                }
            }
            
        }
        return false;
    }
    
    //add customer waiting time
    public void addTime(long time){
        waitingTime.add(time);
    }
    
    //return list of customer waiting for table
    public List<Customer> notifyCust(){
        return waitingCustomers;
    }
    
    //return list of custome that enter cafe
    public List<Customer> customersEnter(){
        return customersEnter;
    }
    
    //return list of customer waiting time 
    public List<Long> waitingTime(){
        return waitingTime;
    }
    
    //set customer who leave
    public void leave(Customer customer){
        delCustomer = customer;
    }
    
    //return customer who leave for landlord to delete 
    public Customer delCustomer(){
        return delCustomer;
    }

    //set cafe closing time
    public void setClosingTime() {
        synchronized(listCustomer){
            closingTime = true;
            listCustomer.notifyAll();
        }
    }
    
    //notify landlord that barmaid has left
    public  void setMaidLeft(){
        synchronized(this){
            maidLeft = true;
            notify();
        }
    }
    
    //notify landlord that assistant has left
    public  void setAstLeft(){
        synchronized(this){
            astLeft = true;
            notify();
        }
    }
    
    //return boolean value of assistant that left
    public boolean astLeft(){
        return astLeft;
    }
    
    //return boolean value of barmaid that left
    public boolean maidLeft(){
        return maidLeft;
    }
    
    //wait method for landlord before closing cafe
    public  void waitForClosing(){
        synchronized(this){
             while (!maidLeft || !astLeft) {
                System.out.println(CColor.CYAN + "Landlord waiting for everyone to leave");
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cafe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
       
    }
    
    //counter for customers that are served
    public void servedCounter(){
        custServed++;
    }
    
    //return counter value
    public int custServed(){
        return custServed;
    }

}
