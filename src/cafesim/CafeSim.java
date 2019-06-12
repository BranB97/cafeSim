/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafesim;

/**
 *
 * @author acer
 */
public class CafeSim {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Code to run cafe simulation
        Config config = new Config();
        config.input();
        Cup cup = new Cup();
        Glass glass = new Glass();
        Ingredients ing = new Ingredients();
        Cupboard cb = new Cupboard(glass, cup, ing);
        fountainTap ft = new fountainTap();
        Cafe cafe = new Cafe();
        
        Customer cust = new Customer(cafe);
        Assistant ast = new Assistant(cafe, cb, cust);
        Landlord lord = new Landlord(cafe, cb, ft, cust, ing, cup, glass, ast);
        Barmaid maid = new Barmaid(cafe, cb, ft, cust, ing, cup, glass, ast);
        CustGenerator cg = new CustGenerator(cafe);
        Clock clock = new Clock(lord, maid, cg, ast, cafe);
        
        Thread thlord = new Thread(lord);
        Thread thmaid = new Thread(maid);
        Thread thast = new Thread(ast);
        Thread thcg = new Thread(cg);
        Thread thclock = new Thread(clock);
        
        thcg.start();
        thlord.start();
        thmaid.start();
        thast.start();
        thclock.start();
        
        
        
    }
    
}
