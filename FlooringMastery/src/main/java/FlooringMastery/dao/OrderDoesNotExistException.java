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
public class OrderDoesNotExistException extends Exception {
    public OrderDoesNotExistException(String message){
        super(message);
    }
    
    public OrderDoesNotExistException(String message, Throwable cause){
        super(message, cause);
    }
}
