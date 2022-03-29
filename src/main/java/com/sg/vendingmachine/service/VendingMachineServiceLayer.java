package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.dto.Item;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author darrylanthony
 */
public interface VendingMachineServiceLayer {
    
    /**
     * Let's use insert funds.
     * @param money
     * @return
     * @throws VendingMachinePersistenceException 
     */
    BigDecimal insertFunds(String money)throws VendingMachinePersistenceException;
    
    
    /**
     * Lets use purchase an item.
     * @param itemID
     * @return
     * @throws VendingMachinePersistenceException 
     */
    BigDecimal purchaseItem(int itemID)throws VendingMachinePersistenceException, FileNotFoundException, InsufficientFundsException;
    
    
    /**
     * Gets user an item.
     * @param itemID
     * @return
     * @throws VendingMachinePersistenceException 
     */
    Item getItem(int itemID) throws VendingMachinePersistenceException, FileNotFoundException,NoItemInventoryException;
    
    /**
     * Displays an item based on the id.
     * @param itemID
     * @return
     * @throws VendingMachinePersistenceException 
     */
    Item viewItem(int itemID) throws VendingMachinePersistenceException, FileNotFoundException;
    
    
    
    /**
     * Displays the current stocks.
     * @param cashInsert
     * @return
     * @throws VendingMachinePersistenceException 
     */
    public List<Item> getCurrentStock()throws VendingMachinePersistenceException, FileNotFoundException;
    
    
    /**
     * Updates the stock after a purchase.
     * @param ID
     * @return
     * @throws VendingMachinePersistenceException 
     */
    Item updateItem(int itemID) throws VendingMachinePersistenceException, FileNotFoundException,NoItemInventoryException;
    
    /**
     * Converts change into coins.
     * @param change
     * @return
     * @throws VendingMachinePersistenceException 
     */
    Change makeChange(BigDecimal change)throws VendingMachinePersistenceException;
    
    /**
     * Shows user credit.
     * @return 
     */
    String getBalance();
}
