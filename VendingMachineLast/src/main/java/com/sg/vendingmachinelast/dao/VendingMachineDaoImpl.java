/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinelast.dao;

import com.sg.vendingmachinelast.dto.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jeffolupo
 */
public class VendingMachineDaoImpl implements VendingMachineDao {

    private Map<String, Item> products = new HashMap<>();
    
    public VendingMachineDaoImpl(){
        Item item1 = new Item(1);
        item1.setItem("Snickers");
        item1.setPrice("1.85");
        item1.setQuantity("9");
        
        String itemId1 = Integer.toString(item1.getId());
        products.put(itemId1, item1);
        
        Item item2 = new Item(2);
        item2.setItem("M&M");
        item2.setPrice("1.50");
        item2.setQuantity("9");
        
        String itemId2 = Integer.toString(item2.getId());
        products.put(itemId2, item2);
        
        Item item3 = new Item(3);
        item3.setItem("Pringles");
        item3.setPrice("2.10");
        item3.setQuantity("9");
        
        String itemId3 = Integer.toString(item3.getId());
        products.put(itemId3, item3);
        
        Item item4 = new Item(4);
        item4.setItem("Reese's");
        item4.setPrice("1.85");
        item4.setQuantity("9");
        
        String itemId4 = Integer.toString(item4.getId());
        products.put(itemId4, item4);
        
        Item item5 = new Item(5);
        item5.setItem("Pretzels");
        item5.setPrice("1.25");
        item5.setQuantity("9");
        
        String itemId5 = Integer.toString(item5.getId());
        products.put(itemId5, item5);
        
        Item item6 = new Item(6);
        item6.setItem("Twinkies");
        item6.setPrice("1.95");
        item6.setQuantity("9");
        
        String itemId6 = Integer.toString(item6.getId());
        products.put(itemId6, item6);
        
        Item item7 = new Item(7);
        item7.setItem("Doritos");
        item7.setPrice("1.75");
        item7.setQuantity("9");
        
        String itemId7 = Integer.toString(item7.getId());
        products.put(itemId7, item7);
        
        Item item8 = new Item(8);
        item8.setItem("Almond Joy");
        item8.setPrice("1.85");
        item8.setQuantity("9");
        
        String itemId8 = Integer.toString(item8.getId());
        products.put(itemId8, item8);
        
        Item item9 = new Item(9);
        item9.setItem("Trident");
        item9.setPrice("1.95");
        item9.setQuantity("9");
        
        String itemId9 = Integer.toString(item9.getId());
        products.put(itemId9, item9);
    }
        

    @Override
    public List<Item> getAllItems() {
        return new ArrayList<Item>(products.values());
    }

    @Override
    public void removeSelection(String itemId) {
         products.remove(itemId);
       
    }

    @Override
    public void updateItem(String itemId, Item item) {
        products.put(itemId, item);
    }

    @Override
    public Item addNewItem(String itemId, Item item) {
        Item newItem = products.put(itemId, item);
        return newItem;
    }

    @Override
    public Item getItem(int itemId) {
        String idString = Integer.toString(itemId);
        Item product = products.get(idString);
        return product;
    }

}
