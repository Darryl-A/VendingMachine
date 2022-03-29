package com.sg.vendingmachine.service;

/**
 *
 * @author darrylanthony
 */
public class InsufficientFundsException extends Exception{
    //Exception for when user doesn't add enough funds.
    public InsufficientFundsException(String message) {
        super(message);
    }
    
    //Exception for other implementation related exceptions
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}