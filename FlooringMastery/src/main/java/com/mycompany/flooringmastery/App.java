/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery;

import FlooringMastery.controller.controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jeffolupo
 */
public class App {
    
    public static void main(String[] args) {
        
//        controller controller = new controller();
//        controller.run();

        //UserIO io = new UserIOConsoleImpl();
        //FlooringView view = new FlooringView(io);
        //FlooringOrderDao dao = new FlooringOrderDaoImpl();
        //FlooringProductDao pdao= new FlooringProductDaoImpl();
        //FlooringTaxesDao tdao= new FlooringTaxesDaoImpl();
        //OrderNumberDao ndao= new OrderNumberDaoImpl();
        //FlooringService service = new FlooringServiceImpl(dao, pdao, tdao, ndao);
        //controller control = new controller(view, service);
        //control.run();
        
        ApplicationContext ctx = 
                new ClassPathXmlApplicationContext("applicationContext.xml");
        controller controller = 
                ctx.getBean("controller", controller.class);
        controller.run();
    }
        
    
}
