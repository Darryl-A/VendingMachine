package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.dto.Item;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author darrylanthony
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao{
    public Item item1;
    public Item item2;

    public VendingMachineDaoStubImpl(){
        //Initialize two new items
        
        //First item
        item1 = new Item(1);
        item1.setInventory(3);
        item1.setItemCost(new BigDecimal("0.50"));
        item1.setItemName("First Test Snack");
        
        //Second item
        item2 = new Item(2);
        item2.setInventory(0);
        item2.setItemCost(new BigDecimal("1.75"));
        item2.setItemName("Second Test Snack");
    }
    
    public VendingMachineDaoStubImpl(Item testSnack){
        this.item1 = testSnack;
    }
    
    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException, FileNotFoundException {
        List<Item> snackList = new ArrayList<>();
        snackList.add(item1);
        snackList.add(item2);
        return snackList;
    }

    @Override
    public Item getItem(int id) throws VendingMachinePersistenceException, FileNotFoundException {
        if(id == item1.getId()){
            return item1;
        }else{
            return null;
        }
        
    }

    @Override
    public void updateStock(int id) throws VendingMachinePersistenceException, FileNotFoundException {
        item1.setInventory(item1.getInventory() - 1);
    }
    @Override
    public void addSnack(Item snack, int id){
    }
}