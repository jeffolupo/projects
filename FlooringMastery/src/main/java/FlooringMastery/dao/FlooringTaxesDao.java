/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.StateTax;

/**
 *
 * @author jeffolupo
 */
public interface FlooringTaxesDao {
    
    StateTax getTaxRate(String key) throws FlooringDaoPersistenceException ;

    
}
