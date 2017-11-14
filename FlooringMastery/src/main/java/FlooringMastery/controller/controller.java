/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.controller;

import FlooringMastery.dao.FlooringDaoPersistenceException;
import FlooringMastery.dao.OrderDoesNotExistException;
import FlooringMastery.dto.Order;
import FlooringMastery.service.FlooringService;
import FlooringMastery.service.FlooringServiceImpl;
import FlooringMastery.service.InvalidDateException;
import FlooringMastery.ui.FlooringView;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeffolupo
 */
public class controller {

    FlooringView view;
    FlooringService service;

    public controller(FlooringView view, FlooringService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean loop = true;
        int menuSelection = 0;

        while (loop) {
            try {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        displayAllOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        saveOrders();
                        break;
                    case 6:
                        loop = false;
                        goodByeMessage();
                        break;

                }

            }
            catch (InvalidDateException | NumberFormatException | FlooringDaoPersistenceException
                | OrderDoesNotExistException | FileNotFoundException e) {
            view.errorMessage(e.getMessage());
        }

        }

    }

    private int getMenuSelection() {
        return view.mainMenuSelection();
    }

    private void displayAllOrders() throws InvalidDateException, NumberFormatException, FileNotFoundException, FlooringDaoPersistenceException {
        String userDateInput = view.userDateInput();
        List<Order> allOrders = service.listOrderFromDao(userDateInput);
        view.showAllOrders(allOrders);

    }

    private void addOrder() throws FlooringDaoPersistenceException, FileNotFoundException, InvalidDateException {
        Order currentOrder = view.getNewOrderInfo();
        Order completedOrderInfo = service.completeNewOrderInfo(currentOrder);
        if (view.displayAndConfirmOrderSummary(currentOrder).equalsIgnoreCase("yes")) {
            service.addOrderToDao(completedOrderInfo);
        }
    }

    private void editOrder() throws FlooringDaoPersistenceException, FileNotFoundException, OrderDoesNotExistException, InvalidDateException {
        ArrayList<String> list = view.infoNeededToEditOrder();
        Order currentOrder = service.getOrderFromDao(list);
        Order currentOrderEdited = view.editedOrder(currentOrder);
        Order currOrderEditedCompleted = service.completeExistingOrderInfo(currentOrderEdited);
        service.addOrderToDaoAfterEdit(currOrderEditedCompleted);
        view.displayOrderSummary(currOrderEditedCompleted);
        service.listWithRemovedOrder(list);
    }

    private void removeOrder() throws OrderDoesNotExistException, FlooringDaoPersistenceException, FileNotFoundException, InvalidDateException {
        ArrayList<String> list = view.infoNeededToEditOrder();
        service.listWithRemovedOrder(list);
        view.orderHasBeenRemovedMessage();
        service.clearMap();
    }

    private void saveOrders() throws FlooringDaoPersistenceException, FileNotFoundException {
        service.saveOrdersToDao();
        view.orderHasBeenSaved();
    }

    private void goodByeMessage() {
        view.goodByeMessage();
    }
}
