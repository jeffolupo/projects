/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.btpblog.dao;

import com.sg.btpblog.model.Category;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author jeffolupo
 */
public class CategoryDaoStubImpl implements CategoryDao {
    
    List<Category> categories = new ArrayList<>();

    public CategoryDaoStubImpl() {
        
        Category cat1 = new Category();
        cat1.setCategoryId(1);
        cat1.setCategoryName("category1");
        categories.add(cat1);

        Category cat2 = new Category();
        cat2.setCategoryId(2);
        cat2.setCategoryName("category2");
        categories.add(cat2);

        Category cat3 = new Category();
        cat3.setCategoryId(3);
        cat3.setCategoryName("category3");
        categories.add(cat3);
    }

    @Override
    public void addCategory(Category category) {
        // not needed
    }

    @Override
    public void deleteCategory(int categoryId) {
        // not needed
    }

    @Override
    public void updateCategory(Category category) {
        // not needed
    }

    @Override
    public Category getCategoryById(int category) {
        return categories.get(category - 1);
    }

    @Override
    public List<Category> getAllCategories() {
        // not needed
        return categories;
    }

}
