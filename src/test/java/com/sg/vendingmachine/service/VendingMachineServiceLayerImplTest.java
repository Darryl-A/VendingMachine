package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *
 * @author darrylanthony
 */
public class VendingMachineServiceLayerImplTest {
    
    private VendingMachineServiceLayer service;
    
    public VendingMachineServiceLayerImplTest(){
        
        //VendingMachineDao dao = new VendingMachineDaoStubImpl();
        //VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
        //service = new VendingMachineServiceLayerImpl(dao,auditDao);
        
        //After the SpringDI
        ApplicationContext ctx = 
        new ClassPathXmlApplicationContext("applicationContext.xml");
        service = 
        ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);
          
    }

    @Test
    public void testGetCurrentStock() throws VendingMachinePersistenceException, FileNotFoundException {
       //Make copies of the two items created in DaoStubImpl
       
       //First item
        Item item1Clone = new Item(1);
        item1Clone.setInventory(3);
        item1Clone.setItemCost(new BigDecimal("0.50"));
        item1Clone.setItemName("First Test Snack");
        
        //Second item
        Item item2Clone = new Item(2);
        item2Clone.setInventory(0);
        item2Clone.setItemCost(new BigDecimal("0.75"));
        item2Clone.setItemName("Second Test Snack");
        
        //Act and Assert
        assertEquals(1,service.getCurrentStock().size(), "It should have 1 item only");
        assertTrue(service.getCurrentStock().contains(item1Clone), "Should only contain First Test Snack");
        assertFalse(service.getCurrentStock().contains(item2Clone), "Should only contain Second Test Snack");
    }
    @Test
    public void testGetItem() throws VendingMachinePersistenceException, FileNotFoundException, NoItemInventoryException{
        //Arrange step - Create a new item
        
        Item item1Clone = new Item(1);
        item1Clone.setInventory(3);
        item1Clone.setItemCost(new BigDecimal("0.50"));
        item1Clone.setItemName("First Test Snack");
        
        //Assertionss
        Item retrieved = service.getItem(1);
        assertNotNull(retrieved, "Should not be null");
        assertEquals(item1Clone, retrieved, "It should only contain First Test Snack");
    }
    
    @Test
    public void testAddMoney() throws VendingMachinePersistenceException{
        //Arrange
        BigDecimal testMoney = new BigDecimal("2.50");
        
        
        //Assert
        BigDecimal addedMoney = service.insertFunds("2.50");
        
        assertEquals(testMoney,addedMoney,"Both should be the same big decimal number");
    }
    
    @Test
    public void testViewItem() throws VendingMachinePersistenceException, FileNotFoundException, NoItemInventoryException{
        //Arrange - Create a new item
        Item item1Clone = new Item(1);
        item1Clone.setInventory(3);
        item1Clone.setItemCost(new BigDecimal("0.50"));
        item1Clone.setItemName("First Test Snack");
        
        //Assert
        Item retrieved = service.viewItem(1);
        assertNotNull(retrieved, "Should not be null");
        assertEquals(item1Clone, retrieved, "Should first First Test Snack");
    }
    
    @Test
    public void testUpdateItem() throws VendingMachinePersistenceException, FileNotFoundException, NoItemInventoryException{
        //Arrange - update an item
        int num = 2;
        Item testItem = service.updateItem(1);
        int result = testItem.getInventory();
        
        //Assert
        assertEquals(num,result, "Should be same after update");
    }
    
    @Test
    public void testPurchaseItem() throws VendingMachinePersistenceException, FileNotFoundException, InsufficientFundsException{
        //Arrange
        BigDecimal test = new BigDecimal("1.00");
        BigDecimal addedMoney = service.insertFunds("1.50");
        
        //Act and assert
        BigDecimal result = service.purchaseItem(1);
        assertEquals(test, result, "They should be the same bigdecimal after the test was charged");
    }
    
    @Test
    public void testCalculateChange() throws VendingMachinePersistenceException{
        //Arrange
        Change test = new Change();
        test.setQuarters(new BigDecimal("2"));
        test.setDimes(new BigDecimal("1"));
        test.setNickels(new BigDecimal("1"));
        test.setPennies(new BigDecimal("3"));
        
        //Assert
        Change result = service.makeChange(new BigDecimal("0.68"));
        assertEquals(test.getQuarters(),result.getQuarters(), "They should be 2 quarters");
        assertEquals(test.getDimes(),result.getDimes(), "They should be 1 dime");
        assertEquals(test.getNickels(),result.getNickels(), "They should be 1 nickel");
        assertEquals(test.getPennies(),result.getPennies(), "They should be 3 pennies");
    }
}