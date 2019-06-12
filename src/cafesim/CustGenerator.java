/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafesim;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class CustGenerator implements Runnable {
    
    private Cafe cafe;
    private int maxCustomers = Config.nCust;
    private int count;
    private boolean lastOrders = false;
    
    //initliaze values in customer generator class
    public CustGenerator(Cafe cafe) {
        this.cafe = cafe;
    }

    //customer generator run method
    @Override
    public void run() {
        while(!lastOrders) {
            if(count != maxCustomers){
                Customer customer = new Customer(cafe);
                customer.setInTime(new Date());
                Thread thcustomer = new Thread(customer);
                customer.setName("Customer " + thcustomer.getId());
                thcustomer.start();
                count++;
            }
            try {
                TimeUnit.SECONDS.sleep((long)(Math.random()*4));
            } catch (InterruptedException ex) {
                Logger.getLogger(CustGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(lastOrders) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CustGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //notify generator that last orders has announced
    public synchronized void setLastOrders(){
        lastOrders = true;
        System.out.println(CColor.GREEN +"LeBlanc Cafe is about to close!");
    }
    
    
    
    
}
