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

/**
 *
 * @author jeffolupo
 */
public class FlooringOrderDaoStubImpl implements FlooringOrderDao {

    Order onlyOrder;
    List<Order> orderList = new ArrayList<>();

    public FlooringOrderDaoStubImpl() {
        onlyOrder = new Order();
        onlyOrder.setCustomerName("jeff");
        onlyOrder.setArea("222");
        onlyOrder.setProductType("wood");
        onlyOrder.setState("MN");

        orderList.add(onlyOrder);
    }

    @Override
    public List<Order> displayAllItems(String date) throws FlooringDaoPersistenceException, FileNotFoundException {
        return orderList;
    }

    @Override
    public List<Order> removeSelection(ArrayList<String> list) throws FlooringDaoPersistenceException, OrderDoesNotExistException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order updateItem(ArrayList<String> list) throws FlooringDaoPersistenceException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> addNewItem(String itemId, Order order) throws FlooringDaoPersistenceException, FileNotFoundException {

        List<Order> currList = new ArrayList<>();
        if (itemId.equals(order.getCustomerName())) {
            currList.add(order);
            return currList;
        } else {
            return null;
        }

    }

    @Override
    public Order getItem(ArrayList<String> list) throws FlooringDaoPersistenceException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOrders() throws FlooringDaoPersistenceException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clearMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> changeOrderInfo(String date, Order order) throws FlooringDaoPersistenceException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
