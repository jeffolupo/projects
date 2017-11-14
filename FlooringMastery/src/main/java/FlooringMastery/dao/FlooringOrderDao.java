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
public interface FlooringOrderDao {
    
    List<Order> displayAllItems(String date) throws FlooringDaoPersistenceException, FileNotFoundException;
    
    List<Order> removeSelection(ArrayList<String> list) throws FlooringDaoPersistenceException,
            OrderDoesNotExistException, FileNotFoundException;
    
    Order updateItem(ArrayList<String> list) throws FlooringDaoPersistenceException, FileNotFoundException;
    
    List <Order> addNewItem(String itemId, Order order) throws FlooringDaoPersistenceException, 
            FileNotFoundException;
    
    Order getItem(ArrayList<String> list) throws FlooringDaoPersistenceException, FileNotFoundException;
    
    void saveOrders() throws FlooringDaoPersistenceException, FileNotFoundException;

    void clearMap();
    
    List<Order> changeOrderInfo(String date, Order order) throws FlooringDaoPersistenceException, FileNotFoundException;
}
