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
public class Barmaid implements Runnable {
    private Cafe cafe;
    private Customer cust;
    private Ingredients ing;
    private Cup cup;
    private Glass glass;
    private Assistant ast;
    public final static String MAID_IDENTITY = "Barmaid";
    private Cupboard cb;
    private fountainTap ft;
    private int receiveCup;
    private long make_time = Config.makeCappu;
    
    private boolean cappucino;
    
    private boolean closingTime = false;
    
    //initialize all values in barmaid class
    public Barmaid(Cafe cafe, Cupboard cb, fountainTap ft, Customer cust, Ingredients ing, Cup cup, Glass glass, Assistant ast){
        this.cafe = cafe;
        this.cb = cb;
        this.ft = ft; 
        this.cust = cust;
        this.ing = ing;
        this.cup = cup;
        this.glass = glass;
        this.ast = ast;
    }

    //landlord run method
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Barmaid.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(CColor.PURPLE + "Barmaid is ready to serve");
        while(!closingTime) {
           cust = cafe.serveArea();
           if(closingTime){
               break;
           }
           cust.orders(MAID_IDENTITY);
           takeOrders(cust.getDrink());
        }
        
        if(closingTime){
            try {
                System.out.println(CColor.PURPLE + "Barmaid: Im leaving now");
                cafe.setMaidLeft();
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Barmaid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //take customer orders and give drink
    public  void takeOrders(int orders){
        if(orders == 1) {
            cust.served();
            do{
                cb.waitCup(MAID_IDENTITY);
                cb.maidWait(MAID_IDENTITY);
                goToCupboard(cb, orders);
            }while(!cb.maidFlag());

            receiveCup = cb.getCup();
            makeCoffee(cb, receiveCup, ing);
            System.out.println(cust.getName() + " received cappucino from " + MAID_IDENTITY);
            cust.drinkServed();
        }
        else {
            cust.served();
            do{
                cb.waitGlass(MAID_IDENTITY);
                goToCupboard(cb, orders);
            }while(!cb.maidFlag());

            makeJuice(ft);
            System.out.println(cust.getName() + " received fruit juice " + MAID_IDENTITY);
            cust.drinkServed();
        }
    }
    
    //go to cupboard
    public void goToCupboard(Cupboard cupboard, int orders) {
        cupboard.maidOpen(orders);
        cupboard.maidClose();
    }
    
    //make cappucino
     public void makeCoffee(Cupboard cupboard, int cup, Ingredients ing){
        if(cupboard.getMilk() && cupboard.getCoffee()){
            try {
                Thread.sleep(make_time);
                System.out.println("Barmaid is preparing cappucino");
                ing.fillCup(receiveCup);
                cappucino = ing.getCappucino();
            } catch (InterruptedException ex) {
                Logger.getLogger(Barmaid.class.getName()).log(Level.SEVERE, null, ex);
            }
            cupboard.maidReturn(cappucino);
            cupboard.maidClose();
        }
    }
    
    //make fruit juice
    public void makeJuice(fountainTap ft){
       ft.maidUse();
       ft.maidExit();
    }
    

    //for barmaid to know its closing time
    public  synchronized void setClosingTime(){
        closingTime = true;
        System.out.println("Barmaid: We are closing now!");
    }
}
