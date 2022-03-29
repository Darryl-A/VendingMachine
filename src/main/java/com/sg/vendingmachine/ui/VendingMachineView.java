package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.util.List;

/**
 *
 * @author darrylanthony
 */
public class VendingMachineView {
    private UserIO io;
    
    public VendingMachineView(UserIO io){
        this.io = io;
    }
    
    public int printMenuAndGetSelection(){
            //Menu option for the user to pick a choice
            io.print("\nMain Menu");
            io.print("1. List Stock");
            io.print("2. View Snack");
            io.print("3. Insert Funds");
            io.print("4. Purchase a Snack");
            io.print("5. View Current Balance");
            io.print("6. Exit");

            return io.readInt("Please select an option"
                    + " from the menu.", 1, 6);
    }
    
    //Successful purchase banner
    public void displayPuchaseSuccessBanner() {
    io.readString(
            "Purchase successful!  Please hit enter to continue");
    }
    
    
    //Banner for displaying the stock
    public void displayStockBanner() {
        io.print("Current Items in Stock");
    }
    
    //Banner for displaying an item
    public void displayItemBanner() {
        io.print("Displaying Item");
    }
    //Banner for adding money
    public void displayInsertMoneyBanner() {
        io.print("Inserting Money");
    }
        
   
    
    //Displays the entire stock to the user.
    public void displayStock(List<Item> inStock){
       for(Item currentSnack: inStock){
           String snackInfo = "ID: " + currentSnack.getId() + " Name: " + currentSnack.getItemName() + " Cost: $" + currentSnack.getItemCost().toString();
           io.print(snackInfo);
       }
    }
    
    //Prints the money user has inserted
    public void displayBalance(String currentBalance){
        io.print(currentBalance);
    }
    
    //Reads the input of money from user.
    public double addMoney(){
        return io.readDouble("Please insert money: ", 0.00, 100.00);
    }
    
    //Gets user choice for the item.
    public int getItemId(){
        return io.readInt("Please enter the ID of desired item", 1,4);
    }
    
    //Displays the user choice
    public void getItem(Item choice){
        io.print(choice.getItemName() + " purchased!");
    }
    
    //Displays information regarding a specific item
    public void viewSnack(Item item){
        io.print(item.getItemName() + " Id: " + item.getId() + " Inventory: " + item.getInventory() + " Cost:$" + item.getItemCost().toString());
    }
    
    //Displays the change after user has purchased an item.
    public void getChange(String change, Change userChange){
        io.print("Your change " + change + " in coins: " + userChange.toString());
    }
    
    //Banner for an error message.
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    //Unknown command banner
    public void displayUnknownCommandBanner() {
        io.print("Error Unknown Command!");
    }
    
    
    //Exit banner
    public void displayExitBanner() {
        io.print("Thank you for using this machine. Good bye!");
    }
}