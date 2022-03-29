package com.sg.vendingmachine.dto;

/**
 *
 * @author darrylanthony
 */
public enum ChangeValue {
    QUARTER("0.25"), DIME("0.10"),NICKEL("0.05"),PENNY("0.01");
    
    private String value;
    ChangeValue(String value){
        this.value = value;
    }
    
    public String getValue(){
        return value;
    }
}