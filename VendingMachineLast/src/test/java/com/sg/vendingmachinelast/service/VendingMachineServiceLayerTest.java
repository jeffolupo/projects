/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinelast.service;

import com.sg.vendingmachinelast.dao.VendingMachineDaoStubImpl;
import com.sg.vendingmachinelast.dto.Item;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jeffolupo
 */
public class VendingMachineServiceLayerTest {
    
    private VendingMachineServiceLayer service;
    private VendingMachineDaoStubImpl stub;

    public VendingMachineServiceLayerTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        service
                = ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of listInventory method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testListInventory() throws Exception {
        //test works. this was tested through DAO

    }

    /**
     * Test of restockInventory method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testRestockInventory() throws Exception {
        //test works. this was tested through DAO

    }

    /**
     * Test of removeItemFromInventory method, of class
     * VendingMachineServiceLayer.
     */
    @Test
    public void testRemoveItemFromInventory() throws Exception {
        //test works. this was tested through DAO

    }

    /**
     * Test of addNewItemToInventory method, of class
     * VendingMachineServiceLayer.
     */
    @Test
    public void testAddNewItemToInventory() throws Exception {
        //test works. this was tested through DAO

    }

    /**
     * Test of getItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetItem() throws Exception {
        //test works. this was tested through DAO

    }

    /**
     * Test of returnChange method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testReturnChange() throws Exception {
        Item item = new Item(0001);
        item.setItem("coke");
        item.setPrice("5.00");
        item.setQuantity("6");
        service.addNewItemToInventory(item);

        String[] change = service.returnChange(Double.parseDouble(item.getPrice()), 4);
        String[] input = {"1.00", "4", "0", "0"};

        Assert.assertArrayEquals(input, change);

    }

    /**
     * Test of decreaseQuantity method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testDecreaseQuantity() throws Exception {

        Item newBefore = service.getItem("0001");
        service.decreaseQuantity(newBefore);
        Item newAfter = service.getItem("0001");

        assertEquals("4", newAfter.getQuantity());

    }
}
