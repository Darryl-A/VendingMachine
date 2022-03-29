package com.sg.vendingmachine.service;

/**
 *
 * @author darrylanthony
 */
public class NoItemInventoryException extends Exception{
    //exception case when something is wrong but it isn't cased by another exception ie one of the fields of a snackisn't valid
    public NoItemInventoryException(String message) {
        super(message);
    }
    
    //exception when something is wrong and is caused by another exception ie implementation-specific exception
    public NoItemInventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}