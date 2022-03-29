package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author darrylanthony
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao{
    
    public final String INVENTORY_FILE;
    public static final String DELIMITER = "::";
    private Map<Integer, Item> items = new HashMap<>();
    public VendingMachineDaoFileImpl(){
        INVENTORY_FILE = "itemInventory.txt";
    }
    
    public VendingMachineDaoFileImpl(String inventoryTextFile){
        INVENTORY_FILE = inventoryTextFile;
    }
    
    
    @Override
    public Item getItem(int itemID) throws VendingMachinePersistenceException, FileNotFoundException{
        loadInventory();
        return items.get(itemID);
    }
    
    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException, FileNotFoundException{
        loadInventory();
        return new ArrayList(items.values());
    }


    @Override
    public void updateStock(int itemID) throws VendingMachinePersistenceException, FileNotFoundException{
        loadInventory();
        Item current = items.get(itemID);
        int stock = current.getInventory();
        stock--;
        current.setInventory(stock);
        items.replace(itemID, current);
        writeInventory();
    }
    
    
    private Item unmarshallSnack(String snackAsText){
    // Reads a line from the file in the following pattern:
    // id::item name::item cost::inventory
    //  [0]     [1]            [2]     [3]     [4]     [5]
        String[] snackTokens = snackAsText.split(DELIMITER);

    //Gets the first entry which is ID
        int snackId = Integer.parseInt(snackTokens[0]);

    //Creates an item based on the ID.
        Item snackFromFile = new Item(snackId);

    //Set all the other attributes of an item

    // Index 1 - Item name
        snackFromFile.setItemName(snackTokens[1]);

    // Index 2 - Item price
        BigDecimal cost = new BigDecimal(snackTokens[2]);
        snackFromFile.setItemCost(cost);

    // Index 3 - Number of items in the inventory
        int snackInventory = Integer.parseInt(snackTokens[3]);
        snackFromFile.setInventory(snackInventory);
        
        return snackFromFile;
    }
    
    private void loadInventory() throws VendingMachinePersistenceException, FileNotFoundException {
        Scanner scanner;

        try {
            // Creates Scanner for reading the file
            scanner = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            //catches the FileNotFoundException and translates it to the DVDLibraryDaoException created
            throw new VendingMachinePersistenceException("Error inventory data could not be loaded into memory.", e);
        }
        //Variable for recent line
        String currentLine;
        //Variable for recent item
        Item currentSnack;
        //Reads line and stores items in the Map.
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a DVD
            currentSnack = unmarshallSnack(currentLine);

            //Adds the entry in the Map.
            items.put(currentSnack.getId(), currentSnack);
        }
        // close scanner
        scanner.close();
    }
    
    private String marshallSnack(Item anItem){
        //Converts record to text for file writing purpose
        //id::item name::item cost::inventory
        
        //ID
        String snackAsText = anItem.getId() + DELIMITER;
        
        //Name
        snackAsText += anItem.getItemName() + DELIMITER;
        
        //Cost
        snackAsText += anItem.getItemCost().toString() + DELIMITER;
        
        //Inventory
        snackAsText += anItem.getInventory();
        return snackAsText;
    }
    
    //Writes all items to the inventory file.  
    private void writeInventory() throws VendingMachinePersistenceException, FileNotFoundException {
        PrintWriter out;
        
        //catches and translates IOExceptions to VendingMachinePersistenceException
        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException( "Error. Item information could not be saved.", e);
        }

        // Write out the items to the inventory.
        String snackAsText;
        List<Item> snackList = this.getAllItems();
        for (Item currentSnack : snackList) {
            snackAsText = marshallSnack(currentSnack);
            out.println(snackAsText);
            out.flush();
        }
        // Close PrintWriter
        out.close();
    }
    
    //Adds a snack to the machine
    public void addSnack(Item snack, int id){
        items.put(id, snack);
    }
}