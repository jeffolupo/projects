/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinelast.dao;

import com.sg.vendingmachinelast.dto.Item;
import java.util.List;

/**
 *
 * @author jeffolupo
 */
public interface VendingMachineDao {
    
    List<Item> getAllItems();
    
    void removeSelection(String itemId);
    
    void updateItem(String itemId, Item item);
    
    Item addNewItem(String itemId, Item item);
    
    Item getItem(int itemId);
}
