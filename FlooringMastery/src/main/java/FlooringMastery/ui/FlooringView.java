/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.ui;

import FlooringMastery.dto.Order;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeffolupo
 */
public class FlooringView {

    UserIO io;
    
    public FlooringView (UserIO io){
        this.io = io;
    }
    
    public int mainMenuSelection() {
        
        io.print("~~~~Main Menu~~~~");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Save Current Work");
        io.print("6. Quit");

        return io.readInt("What would you like to do?", 1, 7);
        
    }

    public String userDateInput() {
        return io.readString("Enter the order date in the following "
                + "format (YYYY-MM-DD)");

    }

    public void errorMessage(String error) {
        io.print(error);
    }

    public void showAllOrders(List<Order> orders) {
        
        io.print("");
        for (Order item : orders) {
            System.out.println(item);
        }
        io.print("");
    }

    public Order getNewOrderInfo() {

        String name = io.readString("Please enter your name");
        String state = io.readString("Please enter your State by abbreviation").toUpperCase();
        String productType = io.readString("Please enter the product you would like"
                + " (Carpet, Laminate, Tile or Wood)").toLowerCase();
        String area = io.readString("Enter the Area of the material you need in Square Feet");

        Order currentOrder = new Order();
        currentOrder.setCustomerName(name);
        currentOrder.setState(state.toUpperCase());
        currentOrder.setProductType(productType);
        currentOrder.setArea(area);

        return currentOrder;

    }

    public void displayOrderSummary(Order order) {
        io.print("");
        io.print("====================");
        io.print("   Order Summary ");
        io.print("====================");
        io.print("");
        io.print("Order Number: " + order.getOrderNumber());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Product Type: " + order.getProductType());
        io.print("Area in sq ft: " + order.getArea());
        io.print("Cost Per sq ft: $" + order.getCostPerSquareFoot());
        io.print("Labor Cost Per sq ft: $" + order.getLaborCostPerSquareFoot());
        io.print("Cost of Material: $" + order.getMaterialCost());
        io.print("Cost of Labor: $" + order.getLaborCost());
        io.print("Tax Rate: $" + order.getTaxRate());
        io.print("Total Tax: $" + order.getTax());
        io.print("");
        io.print("====================");
        io.print("Total Bill: $" + order.getTotal());
        io.print("");
    }

    public String displayAndConfirmOrderSummary(Order order) {

        String orderConfirm = io.readString("Enter 'Yes' if this information is correct;"
                + " or anything else if it is incorrect.");

        if (orderConfirm.equalsIgnoreCase("yes")) {
            
            displayOrderSummary(order);
            return orderConfirm.toLowerCase();

        } else {
            return "no";
        }
    }
    
    public String displayOrderAndConfirmDeletion(Order order) {
        
        String orderConfirm = io.readString("Enter 'Yes' if you are sure you want"
                + " to delete this order");

        if (orderConfirm.equalsIgnoreCase("yes")) {
            
            displayOrderSummary(order);
            return orderConfirm.toLowerCase();

        } else {
            return "no";
        }
    }

    public ArrayList<String> infoNeededToEditOrder() {

        ArrayList<String> orderInfo = new ArrayList<>();

        String date = io.readString("Enter the Order Date: ");
        String number = io.readString("Enter the Order Number: ");

        orderInfo.add(date);
        orderInfo.add(number);

        return orderInfo;
    }

    public Order editedOrder(Order order) {

        Order currentOrder = order;
        String customerName = io.readString("Current customer name is '" + currentOrder.getCustomerName() + "': "
                + "press enter to keep current name, or enter new name to update name");
        String state = io.readString("Current State is '" + currentOrder.getState() + "': "
                + "press enter to keep current State, or enter new State to update State").toUpperCase();
        String productType = io.readString("Current product type is '" + currentOrder.getProductType() + "': "
                + "press enter to keep current product type, or enter new product type to update product").toLowerCase();
        String area = io.readString("Current area per sq ft is '" + currentOrder.getArea() + "': "
                + "Press enter to keep current Area, or enter new area to update area per sq ft");
        
        if (customerName.length() > 0) {
            currentOrder.setCustomerName(customerName);
        }
        if (state.length() > 0) {
            currentOrder.setState(state);
        }
        if (productType.length() > 0) {
            currentOrder.setProductType(productType);
        }
        if (area.length() > 0) {
            currentOrder.setArea(area);
        }

        return currentOrder;
    }
    
    public void orderHasBeenRemovedMessage(){
        io.print("");
        io.print("This order has been removed.");  
        io.print("");
    }
    
    public void orderHasBeenSaved(){
        io.print("");
        io.print("Your order(s) has been saved.");
        io.print("");
        
    }
    
    public void goodByeMessage(){
        io.print("Good Bye");
    }
}
