/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Order;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jeffolupo
 */
public class FlooringOrderDaoTest {
    
    private FlooringOrderDao dao = new FlooringOrderDaoImpl("production");
    
    public FlooringOrderDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FlooringDaoPersistenceException, FileNotFoundException {
        
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of displayAllItems method, of class FlooringOrderDao.
     */
    @Test
    public void testDisplayAllItems() throws Exception {
    }

    /**
     * Test of removeSelection method, of class FlooringOrderDao.
     */
    @Test
    public void testRemoveSelection() throws Exception {
        
        String date = "2017-09-26";
        ArrayList<String>orderDateAndNumber = new ArrayList<>();
        orderDateAndNumber.add(date);
        orderDateAndNumber.add("1");
        
        Order order1 = new Order();
        order1.setOrderNumber("1");
        order1.setCustomerName("jeff");
        order1.setState("MN");
        order1.setTaxRate("6.44");
        order1.setProductType("wood");
        order1.setArea("333");
        order1.setCostPerSquareFoot("2.33");
        order1.setLaborCostPerSquareFoot("3.44");
        order1.setMaterialCost("66.22");
        order1.setLaborCost("64.21");
        order1.setTax("12.33");
        order1.setTotal("142.76");
        
        Order order2 = new Order();
        order2.setOrderNumber("2");
        order2.setCustomerName("JACK");
        order2.setState("MN");
        order2.setTaxRate("6.44");
        order2.setProductType("wood");
        order2.setArea("333");
        order2.setCostPerSquareFoot("2.33");
        order2.setLaborCostPerSquareFoot("3.44");
        order2.setMaterialCost("66.22");
        order2.setLaborCost("64.21");
        order2.setTax("12.33");
        order2.setTotal("142.76");
        
        dao.addNewItem(date, order1);
        dao.addNewItem(date, order2);
        
        List<Order> remOrder = dao.removeSelection(orderDateAndNumber);
        
        assertEquals(2, remOrder.size());
    }

    /**
     * Test of updateItem method, of class FlooringOrderDao.
     */
    @Test
    public void testUpdateItem() throws Exception {
        String date = "2017-09-26";
        ArrayList<String>orderDateAndNumber = new ArrayList<>();
        orderDateAndNumber.add(date);
        orderDateAndNumber.add("3");
        
        Order order1 = new Order();
        order1.setOrderNumber("3");
        order1.setCustomerName("jeff");
        order1.setState("MN");
        order1.setTaxRate("6.44");
        order1.setProductType("wood");
        order1.setArea("333");
        order1.setCostPerSquareFoot("2.33");
        order1.setLaborCostPerSquareFoot("3.44");
        order1.setMaterialCost("66.22");
        order1.setLaborCost("64.21");
        order1.setTax("12.33");
        order1.setTotal("142.76");
        
        dao.addNewItem(date, order1);
        
        Order retOr = dao.updateItem(orderDateAndNumber);
        assertEquals("MN",retOr.getState());
    }

    /**
     * Test of addNewItem method, of class FlooringOrderDao.
     */
    @Test
    public void testAddNewItem() throws Exception {
    }

    /**
     * Test of getItem method, of class FlooringOrderDao.
     */
    @Test
    public void testGetItem() throws Exception {
    }

    /**
     * Test of saveOrders method, of class FlooringOrderDao.
     */
    @Test
    public void testSaveOrders() throws Exception {
    }

    /**
     * Test of clearMap method, of class FlooringOrderDao.
     */
    @Test
    public void testClearMap() {
    }

    /**
     * Test of changeOrderInfo method, of class FlooringOrderDao.
     */
    @Test
    public void testChangeOrderInfo() throws Exception {
    }

    
    
}
