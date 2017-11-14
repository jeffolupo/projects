/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinelast.controller;

import com.sg.vendingmachinelast.dto.Item;
import com.sg.vendingmachinelast.service.InsufficientFunds;
import com.sg.vendingmachinelast.service.NoItemInInventoryException;
import com.sg.vendingmachinelast.service.VendingMachineServiceLayer;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author jeffolupo
 */
@Controller
public class ItemController {

    VendingMachineServiceLayer service;

    double userMoney = 0;
    String selectedItem;
    String message;
    String changeToReturn;

    @Inject
    public ItemController(VendingMachineServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String displayItems(Model model) {

        List<Item> itemList = service.listInventory();
        model.addAttribute("itemList", itemList);
        model.addAttribute("userMoney", userMoney);
        model.addAttribute("selectedItem", selectedItem);
        model.addAttribute("message", message);
        model.addAttribute("changeToReturn", changeToReturn);
        return "index";
    }

    @RequestMapping(value = "/addMoney", method = RequestMethod.POST)
    public String addMoney(HttpServletRequest request) {
        String money = request.getParameter("moneyButton");
        if (money.equals("one")) {
            userMoney += 1.00;
        }
        if (money.equals(".25")) {
            userMoney += .25;
        }
        if (money.equals(".10")) {
            userMoney += .10;
        }
        if (money.equals(".05")) {
            userMoney += .05;
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/selectItem", method = RequestMethod.POST)
    public String selectItem(HttpServletRequest request) {
        selectedItem = request.getParameter("itemButtons");
        return "redirect:/";
    }

    @RequestMapping(value = "/makePurchase", method = RequestMethod.POST)
    public String makePurchase(HttpServletRequest request) throws NoItemInInventoryException, InsufficientFunds {

        try {
            Item currentItem = service.getItem(selectedItem);

            if (currentItem.getQuantity().equals("0")) {
                String[] arr = service.returnChangeWithNoPurchase(userMoney);
                changeToReturn = "Your change is $" + arr[0] + ": " + arr[1] + " quarter(s), "
                        + arr[2] + " dime(s), "
                        + arr[3] + " nickel(s).";
            }

            double itemPrice = Double.parseDouble(currentItem.getPrice());
            String[] arr = service.returnChange(userMoney, itemPrice);
            changeToReturn = "Your change is $" + arr[0] + ": " + arr[1] + " quarter(s), "
                    + arr[2] + " dime(s), "
                    + arr[3] + " nickel(s).";

            message = "Thank You for your Purchase!!! Take your change below.";
            service.decreaseQuantity(currentItem);

        } catch (NoItemInInventoryException | InsufficientFunds e) {
            message = e.getMessage();
        }
        userMoney = 0;
        return "redirect:/";
    }

    @RequestMapping(value = "/returnChange", method = RequestMethod.POST)
    public String clearInputs() throws NoItemInInventoryException, InsufficientFunds {

        String[] arr = service.returnChangeWithNoPurchase(userMoney);
        changeToReturn = "Your change is $" + arr[0] + ": " + arr[1] + " quarter(s), "
                + arr[2] + " dime(s), "
                + arr[3] + " nickel(s).";

        selectedItem = "";
        message = "";
        userMoney = 0;

        return "redirect:/";
    }

}
