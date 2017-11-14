/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.service;

import FlooringMastery.dao.FlooringOrderAuditDao;
import FlooringMastery.dao.FlooringOrderAuditDaoImpl;
import FlooringMastery.dao.FlooringOrderDao;
import FlooringMastery.dao.FlooringOrderDaoStubImpl;
import FlooringMastery.dao.FlooringProductDao;
import FlooringMastery.dao.FlooringProductDaoImpl;
import FlooringMastery.dao.FlooringTaxesDao;
import FlooringMastery.dao.FlooringTaxesDaoImpl;
import FlooringMastery.dao.OrderNumberDao;
import FlooringMastery.dao.OrderNumberDaoImpl;
import FlooringMastery.dto.Order;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jeffolupo
 */
public class FlooringServiceTest {

    private FlooringService service;

    public FlooringServiceTest() {
        FlooringOrderDao dao = new FlooringOrderDaoStubImpl();
        FlooringOrderAuditDao adao = new FlooringOrderAuditDaoImpl();
        FlooringProductDao pdao = new FlooringProductDaoImpl();
        FlooringTaxesDao tdao = new FlooringTaxesDaoImpl();
        OrderNumberDao ndao = new OrderNumberDaoImpl();

        service = new FlooringServiceImpl(dao, pdao, tdao, ndao);
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
     * Test of listOrderFromDao method, of class FlooringService.
     */
    @Test
    public void testListOrderFromDao() throws Exception {
    }

    /**
     * Test of addOrderToDao method, of class FlooringService.
     */
    @Test
    public void testAddOrderToDao() throws Exception {
        Order currOrder = new Order();
        currOrder.setCustomerName("tommy");
        currOrder.setArea("333");
        currOrder.setState("MN");
        currOrder.setProductType("Wood");

        service.addOrderToDao(currOrder);
    }

    @Test
    public void testAddOrderToDaoInvalidState() throws Exception {
        Order currOrder = new Order();
        currOrder.setCustomerName("tina");
        currOrder.setArea("333");
        currOrder.setState("SS");
        currOrder.setProductType("Wood");
        service.addOrderToDao(currOrder);
    }

    /**
     * Test of addOrderToDaoAfterEdit method, of class FlooringService.
     */
    @Test
    public void testAddOrderToDaoAfterEdit() throws Exception {
    }

    /**
     * Test of saveOrdersToDao method, of class FlooringService.
     */
    @Test
    public void testSaveOrdersToDao() throws Exception {
    }

    /**
     * Test of getOrderFromDao method, of class FlooringService.
     */
    @Test
    public void testGetOrderFromDao() throws Exception {
    }

    /**
     * Test of listWithRemovedOrder method, of class FlooringService.
     */
    @Test
    public void testListWithRemovedOrder() throws Exception {
    }

    /**
     * Test of completeNewOrderInfo method, of class FlooringService.
     */
    @Test
    public void testCompleteNewOrderInfo() throws Exception {
    }

    /**
     * Test of completeExistingOrderInfo method, of class FlooringService.
     */
    @Test
    public void testCompleteExistingOrderInfo() throws Exception {
    }

    /**
     * Test of clearMap method, of class FlooringService.
     */
    @Test
    public void testClearMap() {
    }

}
