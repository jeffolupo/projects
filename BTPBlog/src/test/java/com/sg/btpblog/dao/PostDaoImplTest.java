package com.sg.btpblog.dao;

import com.sg.btpblog.model.Post;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PostDaoImplTest {

    PostDao dao;

    public PostDaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        dao = ctx.getBean("postDao", PostDao.class);
        DatabaseInitializer.setKnownGoodState();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddPost() {
        LocalDateTime time = (LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        Post post = new Post();
        post.setOriginalTimestamp(Timestamp.valueOf(time));
        post.setUpdatedTimestamp(Timestamp.valueOf(time));
        post.setTitle("Test");
        post.setContent("Test info");
        post.setIsApproved(Boolean.TRUE);
        post.setIsHidden(Boolean.FALSE);
        post.setCategoryId(1);
        dao.addPost(post);
        Integer postId = post.getPostId();
        Post fromDb = dao.getPostById(postId);
        assertEquals(post, fromDb);

    }

    @Test
    public void testDeletePost() {

        LocalDateTime time = (LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        Post post = new Post();
        post.setOriginalTimestamp(Timestamp.valueOf(time));
        post.setUpdatedTimestamp(Timestamp.valueOf(time));
        post.setTitle("Test");
        post.setContent("Test info");
        post.setIsApproved(Boolean.TRUE);
        post.setIsHidden(Boolean.FALSE);
        post.setCategoryId(1);
        dao.addPost(post);
        Integer postId = post.getPostId();
        Post fromDb = dao.getPostById(postId);
        assertEquals(post, fromDb);
        dao.deletePost(postId);
        assertNull(dao.getPostById(postId));
    }

    @Test
    public void testUpdatePost() {
        LocalDateTime time = (LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        Post post = new Post();
        post.setOriginalTimestamp(Timestamp.valueOf(time));
        post.setUpdatedTimestamp(Timestamp.valueOf(time));
        post.setTitle("Test");
        post.setContent("Test info");
        post.setIsApproved(Boolean.TRUE);
        post.setIsHidden(Boolean.FALSE);
        post.setCategoryId(1);
        dao.addPost(post);
        Integer postId = post.getPostId();

        post.setTitle("Test 2");
        dao.updatePost(post);
        Post fromDb = dao.getPostById(postId);
        assertEquals(post, fromDb);
    }

    @Test
    public void testGetPostById() {
        LocalDateTime time = (LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        Post post = new Post();
        post.setOriginalTimestamp(Timestamp.valueOf(time));
        post.setUpdatedTimestamp(Timestamp.valueOf(time));
        post.setTitle("Test");
        post.setContent("Test info");
        post.setIsApproved(Boolean.TRUE);
        post.setIsHidden(Boolean.FALSE);
        post.setCategoryId(1);
        dao.addPost(post);
        Integer postId = post.getPostId();
        Post fromDb = dao.getPostById(postId);
        assertEquals(post, fromDb);
    }

    @Test
    public void testGetAllPosts() {

        List<Post> posts = dao.getAllPosts();
        assertEquals(1, posts.size());
        assertEquals(1, posts.get(0).getPostId());
    }

    @Test
    public void testGetPostByHashtag() {
        List<Post> posts = dao.getPostByHashtag(1);
        assertEquals(1, posts.size());
    }

    @Test
    public void testGetPostByCategory() {
        List<Post> posts = dao.getPostByCategory(1);
        assertEquals(1, posts.size());
    }
}
