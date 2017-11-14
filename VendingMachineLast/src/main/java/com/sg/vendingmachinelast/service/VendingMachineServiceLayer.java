/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinelast.service;

import com.sg.vendingmachinelast.dao.VendingMachineDao;
import com.sg.vendingmachinelast.dto.Change;
import com.sg.vendingmachinelast.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.inject.Inject;
import org.springframework.context.annotation.Bean;


/**
 *
 * @author jeffolupo
 */

public class VendingMachineServiceLayer {

    VendingMachineDao dao;

    @Inject
    public VendingMachineServiceLayer(VendingMachineDao dao) {
        this.dao = dao;

    }

    public List<Item> listInventory() {
        return dao.getAllItems();
    }

    public void restockInventory(List<Item> items) {
        for (Item item : items) {
            String idString = Integer.toString(item.getId());
            dao.updateItem(idString, item);
        }

    }

    public void removeItemFromInventory(String itemId) {
        dao.removeSelection(itemId);
        
    }

    public void addNewItemToInventory(Item item) {
        
        String idString = Integer.toString(item.getId());
        dao.addNewItem(idString, item);
    }

    public Item getItem(String itemId) throws NoItemInInventoryException {
        
        
        Item currentItem = dao.getItem(Integer.parseInt(itemId));

        validateItemIsAvailable(currentItem);
        return currentItem;

    }

    public String[] returnChange(double money, double price) throws InsufficientFunds {

        

        BigDecimal compareMoney = new BigDecimal(money);
        BigDecimal compareMoneyCurrency = compareMoney.setScale(2, RoundingMode.HALF_EVEN);

        BigDecimal comparePrice = new BigDecimal(price);
        BigDecimal comparePriceCurrency = comparePrice.setScale(2, RoundingMode.HALF_EVEN);

        String[] change = new String[4];

        if (money >= price) {
            Change currentChange = new Change();

            change[0] = compareMoneyCurrency.subtract(comparePriceCurrency).toString();

            currentChange.setQuarters(compareMoneyCurrency.subtract(comparePriceCurrency));
            BigDecimal howManyQuartersInChange = currentChange.quartersToReturn();
            BigDecimal quarterLeftOvers = currentChange.remainderOfQuarters();
            change[1] = howManyQuartersInChange.toString().split("\\.")[0];

            currentChange.setDimes(quarterLeftOvers);
            BigDecimal howManyDimesInChange = currentChange.dimesToReturn();
            BigDecimal dimeLeftOvers = currentChange.remainderOfDimes();
            change[2] = howManyDimesInChange.toString().split("\\.")[0];

            currentChange.setNickles(dimeLeftOvers);
            BigDecimal howManyNickelsInChange = currentChange.nickelsToReturn();
            change[3] = howManyNickelsInChange.toString().split("\\.")[0];

        } else {
            throw new InsufficientFunds("ERROR: You only inserted $" + money + ". "
                    + "Please insert $" + (price - money) + " to make this purchase");

        }
        return change;
    }
    
    public String[] returnChangeWithNoPurchase(double money) {

        BigDecimal compareMoney = new BigDecimal(money);
        BigDecimal compareMoneyCurrency = compareMoney.setScale(2, RoundingMode.HALF_EVEN);


        String[] change = new String[4];

        if (money > 0) {
            Change currentChange = new Change();

            change[0] = compareMoneyCurrency.toString();

            currentChange.setQuarters(compareMoneyCurrency);
            BigDecimal howManyQuartersInChange = currentChange.quartersToReturn();
            BigDecimal quarterLeftOvers = currentChange.remainderOfQuarters();
            change[1] = howManyQuartersInChange.toString().split("\\.")[0];

            currentChange.setDimes(quarterLeftOvers);
            BigDecimal howManyDimesInChange = currentChange.dimesToReturn();
            BigDecimal dimeLeftOvers = currentChange.remainderOfDimes();
            change[2] = howManyDimesInChange.toString().split("\\.")[0];

            currentChange.setNickles(dimeLeftOvers);
            BigDecimal howManyNickelsInChange = currentChange.nickelsToReturn();
            change[3] = howManyNickelsInChange.toString().split("\\.")[0];

        } else {
            change[0] ="0";
            change[1] ="0";
            change[2] ="0";
            change[3] ="0";
        }
        return change;
    }

    private void validateItemIsAvailable(Item item)
            throws NoItemInInventoryException {

        if (item.getQuantity().equals("0")) {
            throw new NoItemInInventoryException("SORRY: " + item.getItem() + " "
                    + "is sold out!!");
        }

    }

    public void decreaseQuantity(Item item) {
        Item currentItem = dao.getItem(item.getId());
        int newQuantity = Integer.parseInt(currentItem.getQuantity());
        newQuantity--;
        String pushBackQuantity = Integer.toString(newQuantity);
        currentItem.setQuantity(pushBackQuantity);
        String idString = Integer.toString(currentItem.getId());
        dao.updateItem(idString, currentItem);
    }
    
}
