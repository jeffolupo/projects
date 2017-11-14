/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.orderNumber;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jeffolupo
 */
public class OrderNumberDaoImpl implements OrderNumberDao{

    public static final String ORDER_NUMBER = "orderNumberTracker.txt";
    
    public List<Integer> orderNum = new ArrayList<>();
    
    @Override
    public int getOrderNumber() throws FlooringDaoPersistenceException {
        loadLibrary();
        int currentOrderNum = orderNum.get(0);        
        return currentOrderNum;
    }
    
    @Override
    public void pushOrderNum(int n) throws FlooringDaoPersistenceException {
        orderNum.clear();
        writeLibrary(n);
    }
    
    

    private void loadLibrary() throws FlooringDaoPersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ORDER_NUMBER)));
        } catch (FileNotFoundException e) {
            throw new FlooringDaoPersistenceException(
                    "-_- Could not load order number into memory.", e);
        }

        // currentLine holds the most recent line read from the file
        String currentLine;
        int[] currentTokens = new int [1];

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();
            currentTokens[0] = Integer.parseInt(currentLine);
            // Create a new DVD object and put it into the map of dvds
            orderNumber number = new orderNumber(currentTokens[0]);

            orderNum.add(number.getOrderNumber());
        }
        scanner.close();
    }

    private void writeLibrary(int n) throws FlooringDaoPersistenceException {
        
                
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ORDER_NUMBER));
        } catch (IOException e) {
            throw new FlooringDaoPersistenceException(
                    "Could not save order number.", e);
        }

        
       
            // write the Student object to the file
            out.println(n);
            // force PrintWriter to write line to the file
            out.flush();
        
        // Clean up
        out.close();

    }
    
}

