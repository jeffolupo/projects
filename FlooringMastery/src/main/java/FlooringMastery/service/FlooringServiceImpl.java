/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.service;

import FlooringMastery.dao.FlooringDaoPersistenceException;
import FlooringMastery.dao.FlooringOrderDao;
import FlooringMastery.dao.FlooringProductDao;
import FlooringMastery.dao.FlooringTaxesDao;
import FlooringMastery.dao.OrderDoesNotExistException;
import FlooringMastery.dao.OrderNumberDao;
import FlooringMastery.dto.Order;
import FlooringMastery.dto.Products;
import FlooringMastery.dto.StateTax;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeffolupo
 */
public class FlooringServiceImpl implements FlooringService {

    FlooringOrderDao dao;
    FlooringProductDao pdao;
    FlooringTaxesDao tdao;
    OrderNumberDao ndao;

    public FlooringServiceImpl(FlooringOrderDao dao, FlooringProductDao pdao,
            FlooringTaxesDao tdao, OrderNumberDao ndao) {
        this.dao = dao;
        this.pdao = pdao;
        this.tdao = tdao;
        this.ndao = ndao;
    }

    @Override
    public List<Order> listOrderFromDao(String date) throws InvalidDateException,
            NumberFormatException, FileNotFoundException, FlooringDaoPersistenceException {

        boolean value = dateValidation(date);
        if (value) {
            List<Order> listOfOrders = dao.displayAllItems(date);
            dao.clearMap();
            return listOfOrders;

        } else {
            throw new InvalidDateException("This date format is incorrect");
        }

    }

    // Might need to add this to as an OverRide
    @Override
    public Order addOrderToDao(Order order) throws FlooringDaoPersistenceException,
            FileNotFoundException {
        LocalDate ld = LocalDate.now();
        String date = ld.toString();
        dao.addNewItem(date, order);
        return order;
    }

    @Override
    public Order addOrderToDaoAfterEdit(Order order) throws FlooringDaoPersistenceException,
            FileNotFoundException {
        LocalDate ld = LocalDate.now();
        String date = ld.toString();
        dao.changeOrderInfo(date, order);
        return order;
    }

    @Override
    public void saveOrdersToDao() throws FlooringDaoPersistenceException, FileNotFoundException {
        dao.saveOrders();
    }

    @Override
    public Order getOrderFromDao(ArrayList<String> list) throws FlooringDaoPersistenceException,
            FileNotFoundException, InvalidDateException {
        boolean value = dateValidation(list.get(0));
        if (value) {
            return dao.getItem(list);
        } else {
            throw new InvalidDateException("This date format is incorrect");
        }
    }

    @Override
    public List<Order> listWithRemovedOrder(ArrayList<String> list) throws OrderDoesNotExistException,
            FlooringDaoPersistenceException, FileNotFoundException, InvalidDateException {

        boolean value = dateValidation(list.get(0));
        if (value) {
            return dao.removeSelection(list);
        } else {
            throw new InvalidDateException("This date format is incorrect");
        }

    }

    // Methods to Complete Order
    @Override
    public Order completeNewOrderInfo(Order currentOrder) throws FlooringDaoPersistenceException, InvalidDateException {

        int orderNumberCount = 1 + ndao.getOrderNumber();
        currentOrder.setOrderNumber(Integer.toString(orderNumberCount));
        ndao.pushOrderNum(orderNumberCount);
        return completeExistingOrderInfo(currentOrder);
    }

    @Override
    public Order completeExistingOrderInfo(Order currentOrder) throws FlooringDaoPersistenceException, InvalidDateException {
        Products productInfo = productValidation(currentOrder);

        StateTax taxInfo = stateValidation(currentOrder);

        currentOrder.setCostPerSquareFoot(productInfo.getCostPerSquareFoot());
        currentOrder.setLaborCostPerSquareFoot(productInfo.getLaborCostPerSquareFoot());
        currentOrder.setTaxRate(taxInfo.getTaxRate());

        currentOrder = bigDecimalOrderInfo(currentOrder);

        return currentOrder;

    }

    @Override
    public void clearMap() {
        dao.clearMap();
    }

    private Order bigDecimalOrderInfo(Order currentOrder) {

        BigDecimal percentage = new BigDecimal("100");

        BigDecimal costPerSqFt = new BigDecimal(currentOrder.getCostPerSquareFoot());
        BigDecimal laborPerSqFt = new BigDecimal(currentOrder.getLaborCostPerSquareFoot());
        BigDecimal areaSqFt = new BigDecimal(currentOrder.getArea());
        BigDecimal taxRate = new BigDecimal(currentOrder.getTaxRate());

        costPerSqFt = costPerSqFt.setScale(2, RoundingMode.HALF_EVEN);
        laborPerSqFt = laborPerSqFt.setScale(2, RoundingMode.HALF_EVEN);
        areaSqFt = areaSqFt.setScale(2, RoundingMode.HALF_EVEN);
        taxRate = taxRate.setScale(2, RoundingMode.HALF_EVEN).divide(percentage);

        BigDecimal materialCost = areaSqFt.multiply(costPerSqFt);
        BigDecimal laborCost = areaSqFt.multiply(laborPerSqFt);
        BigDecimal materialPlusLabor = materialCost.add(laborCost);
        BigDecimal tax = materialPlusLabor.multiply(taxRate);
        BigDecimal total = materialPlusLabor.add(tax);

        materialCost = materialCost.setScale(2, RoundingMode.HALF_EVEN);
        laborCost = laborCost.setScale(2, RoundingMode.HALF_EVEN);
        tax = tax.setScale(2, RoundingMode.HALF_EVEN);
        total = total.setScale(2, RoundingMode.HALF_EVEN);

        currentOrder.setMaterialCost(materialCost.toString());
        currentOrder.setLaborCost(laborCost.toString());
        currentOrder.setTax(tax.toString());
        currentOrder.setTotal(total.toString());

        return currentOrder;

    }

    // Validation that the Date entered by user is Valid!!!
    private Boolean dateValidation(String date) throws InvalidDateException, NumberFormatException {
        String[] stringDate = date.split("-");

        for (String element : stringDate) {
            boolean isInteger = isInteger(element);
            if (!isInteger) {
                throw new NumberFormatException("ERROR: This date entry has letters.");
            }
        }

        if (stringDate.length != 3) {
            throw new InvalidDateException("Error: This date format is incorrect");
        }

        if (stringDate[0].length() != 4 || Integer.parseInt(stringDate[0]) < 2017 || Integer.parseInt(stringDate[0]) > 2017) {
            throw new InvalidDateException("ERROR: This date format is incorrect");
        }

        if (stringDate[1].length() != 2 || stringDate[2].length() != 2 || Integer.parseInt(stringDate[1]) < 1 || Integer.parseInt(stringDate[1]) > 12
                || Integer.parseInt(stringDate[2]) < 1 || Integer.parseInt(stringDate[2]) > 31) {
            throw new InvalidDateException("ERROR: This date format is incorrect");
        }

        return true;
    }

    private boolean isInteger(String s) {
        boolean isValidInteger = false;
        try {
            Integer.parseInt(s);
            isValidInteger = true;
        } catch (NumberFormatException ex) {
        }
        return isValidInteger;
    }

    private StateTax stateValidation(Order order) throws FlooringDaoPersistenceException, InvalidDateException {

        StateTax taxInfo = tdao.getTaxRate(order.getState());

        if (taxInfo != null) {
            return taxInfo;
        } else {
            throw new InvalidDateException("State Entered is not valid");
        }
    }
    
    private Products productValidation(Order order) throws FlooringDaoPersistenceException, 
            InvalidDateException {
        
        Products productInfo = pdao.getProduct(order.getProductType());
        
        if (productInfo !=null){
            return productInfo;
        } else {
             throw new InvalidDateException("Product Entered is not valid");

        }
        
    }

}
