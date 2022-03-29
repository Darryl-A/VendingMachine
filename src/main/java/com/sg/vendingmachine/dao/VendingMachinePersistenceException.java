package com.sg.vendingmachine.dao;

/**
 *
 * @author darrylanthony
 */
public class VendingMachinePersistenceException extends Exception{
    //General exception for unwarranted errors
    public VendingMachinePersistenceException(String message) {
        super(message);
    }
    
    //Exception for other implementation exceptions
    public VendingMachinePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}