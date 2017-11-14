/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinelast.dao;

import com.sg.vendingmachinelast.dto.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeffolupo
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {
    
    Item onlyItem;
    List<Item> itemList = new ArrayList<>();
    
    public VendingMachineDaoStubImpl(){
        onlyItem = new Item(0001);
        onlyItem.setItem("sprite");
        onlyItem.setPrice("5.00");
        onlyItem.setQuantity("5");
        
        itemList.add(onlyItem);
    }

    @Override
    public List<Item> getAllItems() {
        return itemList;
    }

    @Override
    public void removeSelection(String itemId) {
        if(itemId.equals(onlyItem.getId())) {
        
        }
    }

    @Override
    public Item addNewItem(String itemId, Item item) {
        if(itemId.equals(onlyItem.getId())) {
           return onlyItem; 
        } else {
            return null;
        }
    }

    @Override
    public Item getItem(int itemId) {
        if(itemId == onlyItem.getId()) {
           return onlyItem; 
        } else {
            return null;
        }

    }

    @Override
    public void updateItem(String itemId, Item item){
        if(itemId.equals(onlyItem.getId())) {   
        }
    }    
    
}
