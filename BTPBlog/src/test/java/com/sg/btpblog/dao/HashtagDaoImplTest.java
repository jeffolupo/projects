package com.sg.btpblog.dao;

import com.sg.btpblog.model.Hashtag;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HashtagDaoImplTest {

    private HashtagDao dao;

    @Before
    public void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        dao = ctx.getBean("hashtagDao", HashtagDao.class);
        DatabaseInitializer.setKnownGoodState();
    }

    @Test
    public void testAddGetDeleteHashtag() {
        Hashtag hashtag = new Hashtag();
        hashtag.setHashtagName("#new");
        dao.addHashtag(hashtag);
        Integer hashtagId = hashtag.getHashtagId();
        Hashtag fromDb = dao.getHashtagById(hashtagId);
        assertEquals(hashtag, fromDb);
        dao.deleteHashtag(hashtagId);
        assertNull(dao.getHashtagById(hashtagId));
    }

    @Test
    public void testGetHashtagByName() {
        assertEquals("#test", dao.getHashtagByName("#test").getHashtagName());
    }

    @Test
    public void testGetHashtagByPost() {
        assertEquals("#test", dao.getHashtagsByPost(1).get(0).getHashtagName());
    }

    @Test
    public void testGetAllHashtags() {
        List<Hashtag> hashtags = dao.getAllHashtags();
        assertEquals(1, hashtags.size());
        assertEquals((Integer) 1, hashtags.get(0).getHashtagId());
        assertEquals("#test", hashtags.get(0).getHashtagName());
    }

    @Test
    public void testAddUpdateDeleteHashtag() {
        Hashtag hashtag = new Hashtag();
        hashtag.setHashtagName("#old");
        dao.addHashtag(hashtag);
        Integer hashtagId = hashtag.getHashtagId();
        hashtag.setHashtagName("#new");
        dao.updateHashtag(hashtag);
        Hashtag fromDb = dao.getHashtagById(hashtagId);
        assertEquals(hashtag, fromDb);
        dao.deleteHashtag(hashtagId);
        assertNull(dao.getHashtagById(hashtagId));
    }
}
