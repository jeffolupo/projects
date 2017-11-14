/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.StateTax;
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
public class FlooringTaxesDaoImpl implements FlooringTaxesDao{
    
    public static final String TAX_RATE_FILE = "taxRates.txt";
    public static final String DELIMITER = ",";
    
    private Map<String, StateTax > taxes = new HashMap<>();
    
    @Override
    public StateTax getTaxRate(String key) throws FlooringDaoPersistenceException {
        loadProducts(TAX_RATE_FILE);
        StateTax taxRate = taxes.get(key);
        return taxRate;
    }
    
    
    private void loadProducts(String fileName) throws FlooringDaoPersistenceException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new FlooringDaoPersistenceException(
                    "-___- Could not load State Tax data into memory.", e);
        }
        String currentLine;
 
        String [] currentTokens;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            StateTax currentItem = new StateTax();            
            
            currentItem.setState(currentTokens[0]);
            currentItem.setTaxRate(currentTokens[1]);
            
            taxes.put(currentItem.getState(), currentItem);
        }
        scanner.close();
    }
}
