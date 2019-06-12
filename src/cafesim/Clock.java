/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafesim;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class Clock implements Runnable {
    
    private Landlord lord;
    private Barmaid maid;
    private CustGenerator cg;
    private Assistant ast;
    private Cafe cafe;
    
    //initialize values for clock class
    public Clock(Landlord lord, Barmaid maid, CustGenerator cg, Assistant ast, Cafe cafe){
        this.lord = lord;
        this.maid = maid;
        this.cg = cg;
        this.ast = ast;
        this.cafe = cafe;
    }

    //clock run method
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(10);
            lastOrders();
            TimeUnit.SECONDS.sleep(10);
            closeCafe();
        } catch (InterruptedException ex) {
            Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //set last orders for landlord and customer generator
    public void lastOrders(){
        lord.setLastOrders();
        cg.setLastOrders();
    }
    
    //set closing time in cafe
    public  void closeCafe() {
        lord.setClosingTime();
        maid.setClosingTime();
        cafe.setClosingTime();
        ast.setClosingTime();
    }
    
}
