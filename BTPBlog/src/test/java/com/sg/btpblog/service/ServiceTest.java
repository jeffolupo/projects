
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.btpblog.service;

import com.sg.btpblog.dao.CategoryDao;
import com.sg.btpblog.dao.CategoryDaoImpl;

import com.sg.btpblog.dao.CategoryDaoStubImpl;
import com.sg.btpblog.dao.HashtagDao;
import com.sg.btpblog.dao.HashtagDaoImpl;
import com.sg.btpblog.dao.HashtagDaoStubImpl;
import com.sg.btpblog.dao.PageDao;
import com.sg.btpblog.dao.PageDaoImpl;
import com.sg.btpblog.dao.PageDaoStubImpl;

import com.sg.btpblog.dao.PostDao;
import com.sg.btpblog.dao.PostDaoStubImpl;
import com.sg.btpblog.model.Post;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jeffolupo
 */
public class ServiceTest {

    private Service service;

    public ServiceTest() {
        PostDao pdao = new PostDaoStubImpl();
        HashtagDao hdao = new HashtagDaoStubImpl();
        CategoryDao cdao = new CategoryDaoStubImpl();
        PageDao padao = new PageDaoStubImpl();

        service = new ServiceImpl(pdao, hdao, cdao, padao);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testServiceGetPostByHashtag() {
        List<Post> posts = service.getPostByHashtag(1);
        assertEquals(3, posts.size());

    }
    
    @Test
    public void testGetPostByCategory() {

        List<Post> posts = service.getPostByCategory(1);
        List<Post> post2 = service.getPostByCategory(3);
        assertEquals(3, posts.size());
        assertEquals(0, post2.size());

    }

    /**
     * Test of getAllVisiblePosts method, of class Service.
     */
    @Test
    public void testGetAllVisiblePosts() {

        List<Post> posts = service.getAllVisiblePosts();
        assertEquals(6, posts.size());

    }

    /**
     * Test of getNotApprovedPosts method, of class Service.
     */
    @Test
    public void testGetNotApprovedPosts() {

        List<Post> posts = service.getNotApprovedPosts();
        assertEquals(0, posts.size());

    }

}