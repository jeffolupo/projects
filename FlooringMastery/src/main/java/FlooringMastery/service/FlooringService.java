/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.service;

import FlooringMastery.dao.FlooringDaoPersistenceException;
import FlooringMastery.dao.OrderDoesNotExistException;
import FlooringMastery.dto.Order;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeffolupo
 */
public interface FlooringService {
    
    List<Order>listOrderFromDao(String date) throws InvalidDateException, 
            NumberFormatException, FileNotFoundException, FlooringDaoPersistenceException;
           
    
    Order addOrderToDao(Order order) throws FlooringDaoPersistenceException, 
            FileNotFoundException; 
    
    Order addOrderToDaoAfterEdit(Order order) throws FlooringDaoPersistenceException, 
            FileNotFoundException; 
    
    void saveOrdersToDao() throws FlooringDaoPersistenceException, FileNotFoundException;
    
    Order getOrderFromDao(ArrayList<String> list) throws FlooringDaoPersistenceException, 
            FileNotFoundException, InvalidDateException;
    
    List<Order> listWithRemovedOrder(ArrayList<String> list) throws OrderDoesNotExistException, 
            FlooringDaoPersistenceException, FileNotFoundException, InvalidDateException;
    
    public Order completeNewOrderInfo(Order currentOrder) throws FlooringDaoPersistenceException, 
            InvalidDateException; 
    
    Order completeExistingOrderInfo(Order currentOrder) throws FlooringDaoPersistenceException, 
            InvalidDateException; 
    
    void clearMap(); 
}
