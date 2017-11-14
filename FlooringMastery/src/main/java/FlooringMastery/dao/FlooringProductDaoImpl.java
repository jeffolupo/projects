/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Products;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author jeffolupo
 */
public class FlooringProductDaoImpl implements FlooringProductDao{
    
    public static final String PRODUCT_MACHINE_FILE = "product.txt";
    public static final String DELIMITER = ",";
    
    private Map<String, Products > products = new HashMap<>();
    
    @Override
    public Products getProduct(String key) throws FlooringDaoPersistenceException {
        loadProducts(PRODUCT_MACHINE_FILE);
        Products product = products.get(key);
        return product;
    }
    
    
    private void loadProducts(String fileName) throws FlooringDaoPersistenceException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new FlooringDaoPersistenceException(
                    "-___- Could not load Product Information data into memory.", e);
        }
        String currentLine;
 
        String [] currentTokens;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Products currentItem = new Products();            
            
            currentItem.setProductType(currentTokens[0]);
            currentItem.setCostPerSquareFoot(currentTokens[1]);
            currentItem.setLaborCostPerSquareFoot(currentTokens[2]);
            
            products.put(currentItem.getProductType(), currentItem);
        }
        scanner.close();
    }

    
}
