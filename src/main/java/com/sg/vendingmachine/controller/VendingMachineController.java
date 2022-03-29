package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.UserIO;
import com.sg.vendingmachine.ui.UserIOConsoleImpl;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author darrylanthony
 */
public class VendingMachineController {
    
    private VendingMachineServiceLayer service;
    private VendingMachineView view;
    private UserIO io = new UserIOConsoleImpl();
    
    //Constructor
    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view){
        this.service = service;
        this.view = view;
    }
    
    public void run() throws VendingMachinePersistenceException, FileNotFoundException, NoItemInventoryException, InsufficientFundsException {
        boolean keepGoing = true;
        int menuSelection = 0;
        try{
            while (keepGoing) {
                listStock();
                menuSelection = getMenuSelection();
            
                switch (menuSelection) {
                    case 1:
                        listStock();
                        break;
                    case 2:
                        viewSnack();
                        break;
                    case 3:
                        addMoney();
                        break;
                    case 4:
                        purchaseSnack();
                        break;
                    case 5:
                        viewBalance();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                       unknownCommand();
                }
            }
        exitMessage();
    } catch(VendingMachinePersistenceException e){
        view.displayErrorMessage(e.getMessage());
    }}
    //Gets the option associated with user choice
    private int getMenuSelection(){
        return view.printMenuAndGetSelection();
    }
    
    //Lists the whole stock for user 
    private void listStock() throws VendingMachinePersistenceException, FileNotFoundException{
        view.displayStockBanner();
        List<Item> snackList = service.getCurrentStock();
        view.displayStock(snackList);
    }
    // Unknown Command Banner
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }
    //Unknown Exit Banner 
    private void exitMessage() {
        view.displayExitBanner();
    }
    
    //Displays a single item
    private void viewSnack() throws VendingMachinePersistenceException, FileNotFoundException{
        view.displayItemBanner();
        int id = view.getItemId();
        Item currentSnack = service.viewItem(id);
        view.viewSnack(currentSnack);
    }
    
    //Lets use insert some funds
    private void addMoney() throws VendingMachinePersistenceException{
        view.displayInsertMoneyBanner();
        Double cash = view.addMoney();
        String cashInsert = cash.toString();
        service.insertFunds(cashInsert);
    }
    
    //Lets user see what they put in the machine
    private void viewBalance(){
        String balance = service.getBalance();
        view.displayBalance(balance);
        
    }
    
    //Lets use buy an item
    private void purchaseSnack() throws VendingMachinePersistenceException, FileNotFoundException,NoItemInventoryException, InsufficientFundsException{
        int id = view.getItemId();
        String balance = service.getBalance();
        BigDecimal userBalance = new BigDecimal(balance);
        try{ 
                Item userSnack = service.getItem(id);
                BigDecimal cashLeft = service.purchaseItem(id);
                String leftOverCash = cashLeft.toString();
                Change userChange = service.makeChange(cashLeft);
                service.updateItem(id);
                view.getItem(userSnack);
                view.getChange(leftOverCash, userChange);
                view.displayPuchaseSuccessBanner();

       }catch(NoItemInventoryException e){
            io.print(service.viewItem(id).getItemName() + " is not in stock at the moment.");
        }
       catch(InsufficientFundsException e){
            io.print("Funds not enough. \n Current Balance: " + service.getBalance());
        }
       catch (Exception e){
           io.print("Machine Error. Wait for assistance.");
       }
    }
}