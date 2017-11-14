/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinelast.dao;

import com.sg.vendingmachinelast.dto.Item;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jeffolupo
 */
public class VendingMachineDBImpl implements VendingMachineDao {

    private static final String SQL_INSERT_ITEM
            = "insert into Items "
            + "(ItemID, ItemName, Price, Quantity) "
            + "values (?, ?, ?, ?)";
    private static final String SQL_DELETE_ITEM
            = "delete from Items where ItemID = ?";
    private static final String SQL_SELECT_ITEM
            = "select * from Items where ItemID = ?";
    private static final String SQL_UPDATE_ITEM
            = "update Items set "
            + "ItemID = ?, ItemName = ?, Price = ?, "
            + "Quantity = ?"
            + "where ItemID = ?";
    private static final String SQL_SELECT_ALL_ITEMS
            = "select * from Items";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Item> getAllItems() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ITEMS,
                new ItemMapper());
    }

    @Override
    public void removeSelection(String itemId) {
        jdbcTemplate.update(SQL_DELETE_ITEM, itemId);
    }

    @Override
    public void updateItem(String itemId, Item item) {
        jdbcTemplate.update(SQL_UPDATE_ITEM,
                item.getId(),
                item.getItem(),
                item.getPrice(),
                item.getQuantity(),
                itemId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Item addNewItem(String itemId, Item item) {
        jdbcTemplate.update(SQL_INSERT_ITEM,
                item.getId(),
                item.getItem(),
                item.getPrice(),
                item.getQuantity());

        // query the database for the id that was just assigned to the new
        // row in the database
        int newId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);
        // set the new id value on the contact object and return it
        item.setId(newId);
        return item;
    }

    @Override
    public Item getItem(int itemId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ITEM,
                    new ItemMapper(), itemId);
        } catch (EmptyResultDataAccessException ex) {
            // there were no results for the given contact id - we just 
            // want to return null in this case
            return null;
        }
    }

    private static final class ItemMapper implements RowMapper<Item> {

        public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item item = new Item(rs.getInt("ItemID"));
            item.setItem(rs.getString("ItemName"));
            item.setPrice(rs.getString("Price"));
            item.setQuantity(rs.getString("Quantity"));

            return item;
        }
    }

}
