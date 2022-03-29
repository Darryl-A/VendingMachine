package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import static com.sg.vendingmachine.dto.ChangeValue.DIME;
import static com.sg.vendingmachine.dto.ChangeValue.NICKEL;
import static com.sg.vendingmachine.dto.ChangeValue.PENNY;
import static com.sg.vendingmachine.dto.ChangeValue.QUARTER;
import com.sg.vendingmachine.dto.Item;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author darrylanthony
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{
    
    VendingMachineDao dao;
    VendingMachineAuditDao auditDao;
    BigDecimal currentBalance = new BigDecimal("0.00");
    private Change userReturn = new Change();
    
    //Constructor
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao){
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    
    /**
     * Lets user insert funds
     * @param money
     * @return 
     */
    @Override
    public BigDecimal insertFunds(String money) throws VendingMachinePersistenceException{
        BigDecimal inserted = new BigDecimal(money);
        inserted.setScale(2, RoundingMode.HALF_UP);
        currentBalance = currentBalance.add(inserted);
        auditDao.writeAuditEntry(inserted.toString() + " inserted.");
        return currentBalance;
    }
    
    /**
     * Lets user purchase an item from the machine
     * @param id
     * @return
     * @throws VendingMachinePersistenceException
     * @throws FileNotFoundException 
     */
    @Override
    public BigDecimal purchaseItem(int itemID) throws VendingMachinePersistenceException, FileNotFoundException,InsufficientFundsException{
        Item item = dao.getItem(itemID);
        BigDecimal cost = item.getItemCost();
        if(currentBalance.compareTo(item.getItemCost()) == -1){
            throw new InsufficientFundsException("Not enough funds. \n Current Balance: " + currentBalance.toString());
        }
        BigDecimal difference = currentBalance.subtract(cost);
        currentBalance = new BigDecimal("0.00");
        return difference;
    }
    
     /**
     * Updates the count of the item in the stock
     * @param id
     * @return
     * @throws VendingMachinePersistenceException
     * @throws FileNotFoundException
     * @throws NoItemInventoryException 
     */
    @Override
    public Item updateItem(int itemID) throws VendingMachinePersistenceException, FileNotFoundException,NoItemInventoryException{
        Item item = dao.getItem(itemID);
        dao.updateStock(itemID);
        String itemName = item.getItemName();
        auditDao.writeAuditEntry(itemName  + " sold");
        return item;
    }
    
    
    /**
     * Gets user an item he/she has chosen.
     * @param id
     * @return 
     */
    @Override
    public Item getItem(int itemID) throws VendingMachinePersistenceException, FileNotFoundException,NoItemInventoryException{
        Item item = dao.getItem(itemID);
        if(item.getInventory() <= 0){
            throw new NoItemInventoryException(item.getItemName() + " is not in stock at the moment");
        }
        return item;
    }
    
   
    
    
    //Let's user view an item
    @Override
    public Item viewItem(int itemID) throws VendingMachinePersistenceException, FileNotFoundException{
        Item item = dao.getItem(itemID);
        return item;
    }
    
    
    /**
     * Prints whole stock (Uses a lambda stream)
     */
    @Override
    public List<Item> getCurrentStock() throws VendingMachinePersistenceException, FileNotFoundException{
        List<Item> inventory = dao.getAllItems();
        //filters the items with at least 1 inventory to new list
        List<Item> inStock = inventory.stream().filter((p) -> p.getInventory() > 0).collect(Collectors.toList());
        //return the list to be printed out to user using the view
        return inStock;
        
    }
    
    /**
     * Returns change in coins
     * @param change
     * @return 
     */
    @Override
    public Change makeChange(BigDecimal change)throws VendingMachinePersistenceException{
        BigDecimal numOfQuarters = change.divideToIntegralValue(new BigDecimal(QUARTER.getValue()));
        userReturn.setQuarters(numOfQuarters);
        change = change.subtract(numOfQuarters.multiply(new BigDecimal(QUARTER.getValue())));
        BigDecimal numOfDimes = change.divideToIntegralValue(new BigDecimal(DIME.getValue()));
        userReturn.setDimes(numOfDimes);
        change = change.subtract(numOfDimes.multiply(new BigDecimal(DIME.getValue())));
        BigDecimal numOfNickels = change.divideToIntegralValue(new BigDecimal(NICKEL.getValue()));
        userReturn.setNickels(numOfNickels);
        change = change.subtract(numOfNickels.multiply(new BigDecimal(NICKEL.getValue())));
        BigDecimal numOfPennies = change.divideToIntegralValue(new BigDecimal(PENNY.getValue()));
        userReturn.setPennies(numOfPennies);
        change = change.subtract(numOfPennies.multiply(new BigDecimal(PENNY.getValue())));
        return userReturn;
    }
    
    //Prints out the balance to the user.
    @Override
    public String getBalance(){
        return currentBalance.toString();
    }
}