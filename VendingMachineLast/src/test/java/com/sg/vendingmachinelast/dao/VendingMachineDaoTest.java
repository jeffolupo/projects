/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinelast.dao;

import com.sg.vendingmachinelast.dto.Item;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jeffolupo
 */
public class VendingMachineDaoTest {
    
    private VendingMachineDao dao = new VendingMachineDaoImpl();
    
    public VendingMachineDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception{
        List<Item> itemList = dao.getAllItems();
        for(Item item : itemList){
            
            String idString = Integer.toString(item.getId());
            dao.removeSelection(idString);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAllItems method, of class VendingMachineDAO.
     */
    @Test
    public void testGetAllItems() throws Exception {
        
        Item item1 = new Item(0001);
        item1.setItem("sprite");
        item1.setPrice("1.00");
        item1.setQuantity("5");
        
        String idString = Integer.toString(item1.getId());
        dao.addNewItem(idString, item1);
        
        Item item2 = new Item(0002);
        item2.setItem("coke");
        item2.setPrice("1.50");
        item2.setQuantity("5");
        
        String idString2 = Integer.toString(item2.getId());
        dao.addNewItem(idString2, item2);
        
        assertEquals(2, dao.getAllItems().size());
    }

    /**
     * Test of removeSelection method, of class VendingMachineDAO.
     */
    @Test
    public void testRemoveSelection() throws Exception {
        
        Item item1 = new Item(0001);
        item1.setItem("sprite");
        item1.setPrice("1.00");
        item1.setQuantity("5");
        
        String idString = Integer.toString(item1.getId());
        dao.addNewItem(idString, item1);
        
        
        Item item2 = new Item(0002);
        item2.setItem("coke");
        item2.setPrice("1.50");
        item2.setQuantity("5");
        
        String idString2 = Integer.toString(item2.getId());
        dao.addNewItem(idString2, item2);
        
        dao.removeSelection(idString);
        
        assertEquals(1, dao.getAllItems().size());
        
    }

   

    /**
     * Test of getItem method, of class VendingMachineDAO.
     */
    @Test
    public void testGetItem() throws Exception {
        Item item = new Item(0001);
        item.setItem("sprite");
        item.setPrice("1.00");
        item.setQuantity("5");
        
        String idString = Integer.toString(item.getId());
        dao.addNewItem(idString, item);
        
        
        Item fromDao = dao.getItem(item.getId());
        
        assertEquals(item, fromDao);
    }
}
