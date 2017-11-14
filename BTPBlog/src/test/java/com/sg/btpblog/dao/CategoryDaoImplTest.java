package com.sg.btpblog.dao;

import com.sg.btpblog.model.Category;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CategoryDaoImplTest {
    
    private CategoryDao dao;

    @Before
    public void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        dao = ctx.getBean("categoryDao", CategoryDao.class);
        DatabaseInitializer.setKnownGoodState();
    }

    @Test
    public void testAddCategoryAndGetAllCategory() {
        Category cat = new Category();
        cat.setCategoryName("Spicy");
        dao.addCategory(cat);
        
        assertEquals(2, dao.getAllCategories().size());
        
    }

    @Test
    public void testDeleteCategory() {
        
        dao.deleteCategory(1);
        assertNull(dao.getCategoryById(1));
        
    }

    @Test
    public void testUpdateCategoryAndGetCategoryById() {
        Category cat = new Category();
        cat.setCategoryName("Spicy");
        dao.addCategory(cat);
        
        assertEquals("Spicy", dao.getCategoryById(cat.getCategoryId()).getCategoryName());
        
        cat = dao.getCategoryById(cat.getCategoryId());
        int id = cat.getCategoryId();
        
        cat.setCategoryName("bland");
        dao.updateCategory(cat);
        
        assertEquals(id, dao.getCategoryById(cat.getCategoryId()).getCategoryId());
        assertEquals("bland", dao.getCategoryById(cat.getCategoryId()).getCategoryName());
    }

    

}
