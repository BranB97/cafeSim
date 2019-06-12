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
public class fountainTap {

    private long juiceTime = Config.makeJuice;
    private int nLord = 0;
    private int nMaid = 0;
    private int lordWait = 0;
    private int maidWait = 0;
    
    //use method for lord
    public void lordUse() {
        synchronized(this){
            ++lordWait;
            while(nMaid > 0 || maidWait > 0){
                try {
                    System.out.println(CColor.CYAN + "Landlord waiting for fountain tap");
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(fountainTap.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             --lordWait;
            ++nLord;
            System.out.println(CColor.CYAN  + "Landlord is filling up the glass with fountain tap");
        }
        try {
            Thread.sleep(1000);
            synchronized(this){
                fillGlass();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(fountainTap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //exit method for lord
    public void lordExit() {
        synchronized(this){
            --nLord;
            System.out.println(CColor.CYAN + "Landlord is done with the fountain tap");
            if(nLord == 0){
                notify();
            }
        }
    }
    
    //use method for barmaid
    public void maidUse() {
        synchronized(this){
            ++maidWait;
            while(nLord > 0 || lordWait > 0){
                try {
                    System.out.println(CColor.PURPLE + "Barmaid waiting for fountain tap");
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(fountainTap.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            --maidWait;
            ++nMaid;
            System.out.println(CColor.PURPLE +"Barmaid is filling up the glass with fountain tap");
        }
        try {
            Thread.sleep(1000);
            synchronized(this){
                fillGlass();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(fountainTap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //use method for barmaid
    public void maidExit() {
        synchronized(this){
            --nMaid;
            System.out.println(CColor.PURPLE +"Barmaid is done with the fountain tap");
            if(nMaid == 0){
                notify();
            }
        }
    }
    
    //fill fruitjuice in glass
    public void fillGlass(){
        try {
            Thread.sleep(juiceTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(fountainTap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
