/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinelast.service;

/**
 *
 * @author jeffolupo
 */
public class InsufficientFunds extends Exception  {
     public InsufficientFunds(String message) {
        super(message);
    }

    public InsufficientFunds(String message,
            Throwable cause) {
        super(message, cause);
    }
}
