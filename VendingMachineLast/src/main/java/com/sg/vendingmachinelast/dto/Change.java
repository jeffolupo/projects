/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinelast.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author jeffolupo
 */
public class Change {

    private BigDecimal quarters;
    private BigDecimal dimes;
    private BigDecimal nickels;

    public BigDecimal getQuarters() {
        return quarters;
    }

    public void setQuarters(BigDecimal quarters) {
        this.quarters = quarters;
    }

    public BigDecimal getDimes() {
        return dimes;
    }

    public void setDimes(BigDecimal dimes) {
        this.dimes = dimes;
    }

    public BigDecimal getNickles() {
        return nickels;
    }

    public void setNickles(BigDecimal nickels) {
        this.nickels = nickels;
    }
    
    public BigDecimal quartersToReturn (){
       
        BigDecimal quarts1 = new BigDecimal(".25");
        BigDecimal quarts2 = quarts1.setScale(2, RoundingMode.HALF_EVEN);
        
       return quarters.divide(quarts2,2, RoundingMode.CEILING);
    }
    
    public BigDecimal dimesToReturn (){
        
        BigDecimal dimes1 = new BigDecimal(".10");
        BigDecimal dimes2 = dimes1.setScale(2, RoundingMode.HALF_EVEN);
        
       return dimes.divide(dimes2, 2, RoundingMode.CEILING);
    }
    
    public BigDecimal nickelsToReturn (){
       BigDecimal nickels1 = new BigDecimal(".05");
        BigDecimal nickels2 = nickels1.setScale(2, RoundingMode.HALF_EVEN);
        
       return nickels.divide(nickels2, 2, RoundingMode.CEILING);
    }
    
    public BigDecimal remainderOfQuarters (){
        
        BigDecimal quarts1 = new BigDecimal(".25");
        BigDecimal quarts2 = quarts1.setScale(2, RoundingMode.HALF_EVEN); 
        
       return quarters.remainder(quarts2);
    }
    
    public BigDecimal remainderOfDimes (){
       BigDecimal dimes1 = new BigDecimal(".10");
        BigDecimal dimes2 = dimes1.setScale(2, RoundingMode.HALF_EVEN); 
        
       return quarters.remainder(dimes2);
    }
    
    public BigDecimal remainderOfNickels (){
       BigDecimal nickels1 = new BigDecimal(".10");
        BigDecimal nickels2 = nickels1.setScale(2, RoundingMode.HALF_EVEN); 
        
       return quarters.remainder(nickels2);
    }
}
