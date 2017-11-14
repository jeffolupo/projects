package com.sg.btpblog.dao;

import com.sg.btpblog.model.Page;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PageDaoImplTest {

    PageDao dao;

    @Before
    public void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        dao = ctx.getBean("pageDao", PageDao.class);
        DatabaseInitializer.setKnownGoodState(); 
    }

    @Test
    public void testAddPageAndGetAllPagesAndGetPageById() {
        Page p = new Page();
        p.setTitle("PATTT");
        p.setContent("Pat is the man");
        
        dao.addPage(p);
        assertEquals("PATTT", dao.getPageById(p.getPageId()).getTitle());
        assertEquals(2, dao.getAllPages().size());
    }

    @Test
    public void testDeletePage() {
        dao.deletePage(1);
        assertNull(dao.getPageById(1));
    }

    @Test
    public void testUpdatePage() {
        Page p = dao.getPageById(1);
        p.setTitle("Me");
        dao.updatePage(p);
        
        Page fromDb = dao.getPageById(1);
        assertEquals("Me", fromDb.getTitle());
    }


}
