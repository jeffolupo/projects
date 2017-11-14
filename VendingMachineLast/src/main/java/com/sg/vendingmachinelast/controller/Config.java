/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinelast.controller;

import com.sg.vendingmachinelast.dao.VendingMachineDBImpl;
import com.sg.vendingmachinelast.dao.VendingMachineDao;
import com.sg.vendingmachinelast.dao.VendingMachineDaoImpl;
import com.sg.vendingmachinelast.service.VendingMachineServiceLayer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author jeffolupo
 */
//@Configuration
public class Config {
    
    @Bean
    public VendingMachineServiceLayer getService(){
        
        return new VendingMachineServiceLayer(getDao());
    }
    
    @Bean
    public VendingMachineDao getDao (){
        return new VendingMachineDBImpl();
    }
    
    
}
