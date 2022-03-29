package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author darrylanthony
 */
public class VendingMachineDaoFileImplTest {
    VendingMachineDao testDao;
    public VendingMachineDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        String testFile = "testInventory.txt";
        new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(testFile);
    }


    @Test
    public void testGetSnack() throws VendingMachinePersistenceException, FileNotFoundException {
        //Arrange section - Create an item and compare it with the same retrieved one
        Item item1 = new Item(1);
        item1.setInventory(10);
        item1.setItemCost(new BigDecimal("2.50"));
        item1.setItemName("Twix");
        
        testDao.addSnack(item1, 1);
        //Act and assert
        Item retrieved = testDao.getItem(1);
        assertEquals(item1.getId(), retrieved.getId(), "Both IDs should be 1");
        assertEquals(item1.getInventory(), retrieved.getInventory(), "Both should be 10");
        assertEquals(item1.getItemCost(), retrieved.getItemCost(), "Both costs should be 2.50");
        assertEquals(item1.getItemName(), retrieved.getItemName(), "Both names should match - Twix");
        
    }
    
    @Test 
    public void testGetAllSnacks() throws VendingMachinePersistenceException, FileNotFoundException{
        //Arrange - add two items and retrieve them then compare fields.
        Item item1 = new Item(1);
        item1.setInventory(5);
        item1.setItemCost(new BigDecimal("2.50"));
        item1.setItemName("Test snack 1");
        
        Item item2 = new Item(2);
        item2.setInventory(5);
        item2.setItemCost(new BigDecimal("2.50"));
        item2.setItemName("Test snack 2");
        
        testDao.addSnack(item1, 1);
        testDao.addSnack(item2, 2);
        
        //Assert
        List<Item> allItems = testDao.getAllItems();
        
        assertNotNull(allItems,"This list should not be empty");
        assertEquals(2,allItems.size(),"List should contain 2 items as added");
        
        assertTrue(testDao.getAllItems().contains(item1), "List should contain first item");
        assertTrue(testDao.getAllItems().contains(item2), "List should contain second item");
    }
    
    @Test
    public void testUpdateSnackStock() throws VendingMachinePersistenceException, FileNotFoundException{
        //Arrange - Create new item and 
        Item item = new Item(1);
        item.setInventory(5);
        item.setItemCost(new BigDecimal("2.50"));
        item.setItemName("Snickers");
        
        testDao.addSnack(item, 1);
        
        //Assert statement after checking updateStick function.
        testDao.updateStock(1);
        
        assertEquals(4, testDao.getItem(1).getInventory(), "should be 4 after update.");
    }
}
