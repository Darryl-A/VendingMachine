package com.sg.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author darrylanthony
 */
public class Item {
    private String itemName;
    private BigDecimal itemCost;
    private int inventory;
    private int id;
    
    public Item(int id) {
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    
    public String getItemName() {
        return itemName;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.itemName);
        hash = 13 * hash + Objects.hashCode(this.itemCost);
        hash = 13 * hash + this.inventory;
        hash = 13 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.inventory != other.inventory) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }
        return Objects.equals(this.itemCost, other.itemCost);
    }
    
    
}