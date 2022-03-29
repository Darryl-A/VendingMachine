package com.sg.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author darrylanthony
 */
public class Change {
    BigDecimal Quarters;
    BigDecimal Dimes;
    BigDecimal Nickels;
    BigDecimal Pennies;

    public BigDecimal getQuarters() {
        return Quarters;
    }

    public void setQuarters(BigDecimal Quarters) {
        this.Quarters = Quarters;
    }

    public BigDecimal getDimes() {
        return Dimes;
    }

    public void setDimes(BigDecimal Dimes) {
        this.Dimes = Dimes;
    }

    public BigDecimal getNickels() {
        return Nickels;
    }

    public void setNickels(BigDecimal Nickels) {
        this.Nickels = Nickels;
    }

    public BigDecimal getPennies() {
        return Pennies;
    }

    public void setPennies(BigDecimal Pennies) {
        this.Pennies = Pennies;
    }
    
    public String toString(){
        return Quarters.toString() + " quarters, " + Dimes.toString() + " dimes, " + Nickels.toString() + " nickels, " + Pennies.toString() + " pennies.";
    }
}
