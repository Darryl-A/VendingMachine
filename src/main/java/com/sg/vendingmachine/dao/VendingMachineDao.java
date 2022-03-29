package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author darrylanthony
 */
public interface VendingMachineDao {
    
    /**
     * Returns an item based on ID.
     */
    Item getItem(int itemID) throws VendingMachinePersistenceException, FileNotFoundException;

    /**
     * Display all items in the vending machine
     */
    List<Item> getAllItems()throws VendingMachinePersistenceException, FileNotFoundException;
    
    
    /**
     * Updates the stock after a successful purchase
     */
    public void updateStock(int itemID) throws VendingMachinePersistenceException, FileNotFoundException;
    
    /**
     * Adds information of item in the machine
     */
    public void addSnack(Item item, int itemID);
}   
