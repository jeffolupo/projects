/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

/**
 *
 * @author jeffolupo
 */
public interface OrderNumberDao {
    
    int getOrderNumber() throws FlooringDaoPersistenceException;
    
    void pushOrderNum(int n) throws FlooringDaoPersistenceException;
}

