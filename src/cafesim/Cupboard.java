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
public class Cupboard {
    private Glass glass;
    private Cup cup;
    private Ingredients ing;
    
    private int cups;
    
    private boolean milk;
    private boolean coffee;
    private boolean flag;
    private boolean lordFlag = false;
    private boolean maidFlag = false;
    private boolean lordTurn;
    private boolean maidTurn;
    private boolean astTurn;
    
    private int nLord = 0;
    private int nMaid = 0;
    private int nAst = 0;
    private int lordWait = 0;
    private int maidWait = 0;
    private int astWait = 0;
    
    //initialize values for cupboard class
    public Cupboard(Glass glass, Cup cup, Ingredients ing){
        this.glass = glass;
        this.cup = cup;
        this.ing = ing;
    }
    
    // landlord open cupboard
    public void lordOpen(int orders) {
        lordFlag = false;
        synchronized(this){
            ++lordWait;
            while (nMaid > 0 || (maidWait > 0 && maidTurn) || nAst > 0 || (astWait > 0 && astTurn)) {
                try {
                    System.out.println(CColor.CYAN + "Landlord is waiting to use the cupboard");
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cupboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            --lordWait;
            ++nLord;
            System.out.println(CColor.CYAN +"Landlord is opening the cupboard");
        }
        try {
            Thread.sleep(1500);
            synchronized(this){
                if (orders == 1) {
                    if(!ing.maidTaken()){
                        cups = cup.checkCup("Landlord");
                        if(!cup.status()){
                            ing.lordGather();
                            milk = ing.mixMilk();
                            coffee = ing.mixCoffee();
                            lordFlag = true;
                        }
                        else{
                            System.out.println(CColor.CYAN + "Landlord: There are no cups left..");
                            lordFlag = false;
                        }
                    }
                    else{
                        System.out.println(CColor.CYAN + "Landlord found that there are no ingredients");
                        lordFlag = false;
                    }
                } else {
                    glass.checkGlass("Landlord");
                    if(!glass.status()){
                        lordFlag = true;
                    }
                    else{
                        System.out.println(CColor.CYAN + "Landlord: There are no glasses left..");
                        lordFlag = false;
                    }
 
                }
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Cupboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //landlord close cupboard
    public void lordClose() {
        synchronized(this){
            --nLord;
            astTurn = true;
            maidTurn = true;
            lordTurn = false;
            System.out.println(CColor.CYAN + "Landlord is closing the cupboard");
            if (nLord == 0) {
                 notifyAll();
    
            }
        }
    }
    
    // barmaid open cupboard
    public void maidOpen(int orders) {
        synchronized(this){
            maidFlag = false;
            ++maidWait;
            while (nLord > 0 || (lordWait > 0 && lordTurn) || nAst > 0 || (astWait > 0 && astTurn)) {
                try {
                    System.out.println(CColor.PURPLE + "Barmaid is waiting to use the cupboard");
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cupboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            --maidWait;
            ++nMaid;
            System.out.println(CColor.PURPLE +"Barmaid is opening the cupboard");
        }  
        try {
            Thread.sleep(1500);
            synchronized(this){
                if (orders == 1) {
                    if(!ing.lordTaken()){
                        cups = cup.checkCup("Barmaid");
                        if(!cup.status()){
                            ing.maidGather();
                            milk = ing.mixMilk();
                            coffee = ing.mixCoffee();
                            maidFlag = true;
                        }
                        else{
                            System.out.println("Barmaid: There are no cups left..");
                            maidFlag = false;
                        }
                    }
                    else{
                        System.out.println("Barmaid found that there are no ingredients");
                        maidFlag = false;
                    }
                } else {
                    glass.checkGlass("Barmaid");
                    if(!glass.status()){
                        maidFlag = true;
                    }
                    else{
                        System.out.println("Barmaid: There are no glasses left..");
                        maidFlag = false;
                    }
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Cupboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // barmiad open cupboard
    public void maidClose() {
        synchronized(this){
              --nMaid;
              astTurn = true;
              lordTurn = true;
              maidTurn = false;
            System.out.println(CColor.PURPLE +"Barmaid is closing the cupboard");
            if (nMaid == 0) {
                notifyAll();
            }
        }
    }
    
    //return a cup value
    public int getCup() {
        return cups;
    }
    
    //return a milk boolean
    public boolean getMilk(){
        return milk;
    }
    
    //return a coffee boolean
    public boolean getCoffee(){
        return coffee;
    }
    
    //landlord return ingredients
    public void lordReturn(boolean cappucino){
        synchronized(this){
            ++lordWait;
            while (nMaid > 0 || (maidWait > 0 && maidTurn) || nAst > 0 || (astWait > 0 && astTurn)) {
                try {
                    System.out.println(CColor.CYAN + "Landlord is waiting to use the cupboard for return");
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cupboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            --lordWait;
            ++nLord;
            System.out.println(CColor.CYAN +"Landlord is opening the cupboard");
        }

            synchronized(this){
                if(cappucino){
                    flag = false;
                    ing.lordReturn();
                }
            }
    }
    
    //barmaid return ingredients
    public void maidReturn(boolean cappucino){
        synchronized(this){
            ++maidWait;
            while (nLord > 0 || (lordWait > 0 && lordTurn) || nAst > 0 || (astWait > 0 && astTurn)) {
                try {
                    System.out.println(CColor.PURPLE + "Barmaid is waiting to use the cupboard for return");
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cupboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            --maidWait;
            ++nMaid;
            System.out.println(CColor.PURPLE +"Barmaid is opening the cupboard");
        }
        synchronized(this){
                if(cappucino){
                    flag = false;
                    ing.maidReturn();
                }
            }
    }
    
    // landlord wait for ingredients
    public synchronized void lordWait(String person){
        while(ing.maidTaken()){
            try {
                System.out.println(person + " waiting for ingredients to be returned");
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Cupboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //wait for cups to return
    public synchronized void waitCup(String person){
        while(cup.status()){
            try {
                System.out.println(person + " waiting for cups to be returned");
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Cupboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //wait for glass to return
    public synchronized void waitGlass(String person){
        while(glass.status()){
            try {
                System.out.println(person + " waiting for glasses to be returned");
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Cupboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //barmaid wait for ingredients
    public synchronized void maidWait(String person){
        while(ing.lordTaken()){
            try {
                System.out.println(person + " waiting for ingredients to be returned");
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Cupboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //assitant open cupboard
    public void astOpen(Assistant ast){
        synchronized(this){
            ++astWait;
            while (nMaid > 0 || (maidWait > 0 && !astTurn) || nLord > 0 || (lordWait > 0 && !astTurn)) {
                try {
                    System.out.println(CColor.YELLOW + "Assistant is waiting to use the cupboard");
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cupboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            --astWait;
            ++nAst;
            System.out.println(CColor.YELLOW +"Assistant is opening the cupboard");
        }
        try {
            Thread.sleep(1500);
            synchronized(this){
                cup.setQuantity(ast.getCups());
                ast.resetCup();
                glass.setQuantity(ast.getGlass());
                ast.resetGlass();
                cup.setStatus();
                glass.setStatus();
                System.out.println(CColor.YELLOW +"Assistant has placed back the cups and glasses. Cups: " + cup.getQuantity() 
                        + " Glass: "+ glass.getQuantity());
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Cupboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //assistant close cupboard
    public void astClose() {
        synchronized(this){
            --nAst;
            astTurn = false;
            System.out.println(CColor.YELLOW + "Assistant is closing the cupboard");
            if (nAst == 0) {
                 notifyAll();
            }
        }
    }
    
    //return flag boolean to check whether sucessfully obtain cups/glass and ingredients
    public boolean getFlag(){
        return flag;
    }
    
    //set lord flag
    public boolean lordFlag(){
        return lordFlag;
    }
    
    //set maid flag
    public boolean maidFlag(){
        return maidFlag;
    }
    
    
    
}
