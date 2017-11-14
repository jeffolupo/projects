package com.sg.btpblog.dao;

import com.sg.btpblog.model.Category;
import java.util.List;

public interface CategoryDao {

    public void addCategory(Category category);

    public void deleteCategory(int categoryId);

    public void updateCategory(Category category);

    public Category getCategoryById(int category);

    public List<Category> getAllCategories();

}
