/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author jeffolupo
 */
public class FlooringOrderDaoImpl implements FlooringOrderDao {

    LocalDate today = LocalDate.now();
    
    String todayString = today.format(DateTimeFormatter.ISO_DATE);
    String todayDateAsString = todayString.replace("-", "");
    
    public String orderFile = "orders_" + todayDateAsString + ".txt";
    
    public static final String DELIMITER = ",";
    private boolean shouldWrite;
    
    private Map<String, ArrayList<Order>> orders = new HashMap<>();
    
    public FlooringOrderDaoImpl(String mode){   
        this.shouldWrite = mode.equals("production");
    }
    public FlooringOrderDaoImpl(){
    
    }

    @Override
    public ArrayList<Order> displayAllItems(String date) throws FlooringDaoPersistenceException,
            FileNotFoundException {

        String dateFormatted = date.replace("-", "");
        loadLibrary(date, dateFormatted);
        ArrayList<Order> ordersForDate = orders.get(date);

//        if (ordersForDate == null) {
//            throw new OrdersNotFoundException("No orders For This Date");
//        }

        return ordersForDate;
    }

    @Override
    public void clearMap() {
        orders.clear();
    }

    @Override
    public List<Order> removeSelection(ArrayList<String> list) throws FlooringDaoPersistenceException,
            OrderDoesNotExistException, FileNotFoundException {

        orders.clear();
        ArrayList<Order> ordersForDate = displayAllItems(list.get(0));
        Order currentOrder = null;
        ordersForDate = orders.get(list.get(0));
        String orderNumber = list.get(1);

        for (int i = 0; i < ordersForDate.size(); i++) {
            if (ordersForDate.get(i).getOrderNumber().equals(orderNumber)) {
                currentOrder = ordersForDate.remove(i);
                orders.put(list.get(0), ordersForDate);
            }
        }
        if (currentOrder == null) {
            throw new OrderDoesNotExistException("Orders do not exist for this date");
        }

        writeLibrary();
       
        return ordersForDate;
    }

    public List<List> getAllOrders() {
        return new ArrayList<List>(orders.values());
    }

    @Override
    public Order updateItem(ArrayList<String> list) throws FlooringDaoPersistenceException, 
            FileNotFoundException {
        return getItem(list);
    }

    @Override
    public List<Order> addNewItem(String date, Order order) throws FlooringDaoPersistenceException, 
            FileNotFoundException {

        ArrayList<Order> currentList = displayAllItems(date);
        if (orders.containsKey(date)) {
            currentList = orders.get(date);
            currentList.add(order);
            orders.put(date, currentList);
        } else {
            currentList.add(order);
            orders.put(date, currentList);
        }
        writeLibrary();
        return currentList;
    }

    @Override
    public List<Order> changeOrderInfo(String date, Order order) throws FlooringDaoPersistenceException, FileNotFoundException {
        ArrayList<Order> currentList = displayAllItems(date);
        if (orders.containsKey(date)) {
            currentList = orders.get(date);
            currentList.add(order);
            orders.put(date, currentList);
        } else {
            currentList.add(order);
            orders.put(date, currentList);
        }

        writeLibrary();
        return currentList;

    }

    @Override
    public Order getItem(ArrayList<String> list) throws FlooringDaoPersistenceException, FileNotFoundException {

        ArrayList<Order> ordersForDate = displayAllItems(list.get(0));
        Order currentOrder = null;
        ordersForDate = orders.get(list.get(0));
        String orderNumber = list.get(1);

        for (int i = 0; i < ordersForDate.size(); i++) {
            if (ordersForDate.get(i).getOrderNumber().equals(orderNumber)) {
                currentOrder = ordersForDate.remove(i);
            }
        }

        return currentOrder;

    }

    @Override
    public void saveOrders() throws FlooringDaoPersistenceException, FileNotFoundException {
        writeLibrary();
        clearMap();
    }

    private void writeLibrary() throws FlooringDaoPersistenceException {
        if(shouldWrite){
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(orderFile));
        } catch (IOException e) {
            throw new FlooringDaoPersistenceException(
                    "Could not save Order Information.", e);
        }

        List<List> allOrdersInMap = this.getAllOrders();
        for (int i = 0; i < allOrdersInMap.size(); i++) {
            List<Order> ordersDate = new ArrayList<>();
            ordersDate = allOrdersInMap.get(i);
            for (Order currentOrder : ordersDate) {
                // write the Student object to the file
                out.println(currentOrder.getOrderNumber() + DELIMITER
                        + currentOrder.getCustomerName() + DELIMITER
                        + currentOrder.getState() + DELIMITER
                        + currentOrder.getTaxRate() + DELIMITER
                        + currentOrder.getProductType() + DELIMITER
                        + currentOrder.getArea() + DELIMITER
                        + currentOrder.getCostPerSquareFoot() + DELIMITER
                        + currentOrder.getLaborCostPerSquareFoot() + DELIMITER
                        + currentOrder.getMaterialCost() + DELIMITER
                        + currentOrder.getLaborCost() + DELIMITER
                        + currentOrder.getTax() + DELIMITER
                        + currentOrder.getTotal());
                // force PrintWriter to write line to the file
                out.flush();
            }
        }
        // Clean up
        out.close();
        }
        else{
            System.out.println("Not in production Mode, not writing orders to the file");
        }
    }

    private void loadLibrary(String date, String formattedDate) throws FlooringDaoPersistenceException, FileNotFoundException {
        Scanner scanner;

        //    
        File f = new File("orders_" + formattedDate + ".txt");
        if (f.exists()) {

            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(f)));

            String currentLine;

            String[] currentTokens;
            ArrayList<Order> orderArray = new ArrayList<>();

            while (scanner.hasNextLine()) {

                currentLine = scanner.nextLine();
                currentTokens = currentLine.split(DELIMITER);

                // Create a new DVD object and put it into the map of dvds
                Order currentOrder = new Order();
                currentOrder.setOrderNumber(currentTokens[0]);
                currentOrder.setCustomerName(currentTokens[1]);
                currentOrder.setState(currentTokens[2]);
                currentOrder.setTaxRate(currentTokens[3]);
                currentOrder.setProductType(currentTokens[4]);
                currentOrder.setArea(currentTokens[5]);
                currentOrder.setCostPerSquareFoot(currentTokens[6]);
                currentOrder.setLaborCostPerSquareFoot(currentTokens[7]);
                currentOrder.setMaterialCost(currentTokens[8]);
                currentOrder.setLaborCost(currentTokens[9]);
                currentOrder.setTax(currentTokens[10]);
                currentOrder.setTotal(currentTokens[11]);

                orderArray.add(currentOrder);

                orders.put(date, orderArray);

            }
            scanner.close();
        } else {

            orders.put(date, new ArrayList<>());

        }

        // currentLine holds the most recent line read from the file
    }

}



//unit