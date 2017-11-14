

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.btpblog.dao;

import com.sg.btpblog.model.Category;
import com.sg.btpblog.model.Hashtag;
import com.sg.btpblog.model.Post;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeffolupo
 */
public class PostDaoStubImpl implements PostDao {

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    List<Post> posts = new ArrayList<>();
    List<Hashtag> tags = new ArrayList<>();

    Post post1;
    Post post2;
    Post post3;
    Post post4;
    Post post5;
    Post post6;

    public PostDaoStubImpl() {
        
        Hashtag tag1 = new Hashtag();
        tag1.setHashtagId(1);
        tag1.setHashtagName("One");
        tags.add(tag1);

        Post post1 = new Post();
        post1.setHashtags(tags);
        post1.setPostId(1);
        post1.setTitle("Post1");
        post1.setContent("info for post 1");
        post1.setCategoryId(1);
        post1.setIsHidden(Boolean.FALSE);
        post1.setIsApproved(Boolean.TRUE);
        post1.setOriginalTimestamp(timestamp);
        post1.setUpdatedTimestamp(timestamp);
        posts.add(post1);

        Post post2 = new Post();
        post2.setHashtags(tags);
        post2.setPostId(2);
        post2.setTitle("Post2");
        post2.setContent("info for post 2");
        post2.setCategoryId(1);
        post2.setIsHidden(Boolean.FALSE);
        post2.setIsApproved(Boolean.TRUE);
        post2.setOriginalTimestamp(timestamp);
        post2.setUpdatedTimestamp(timestamp);
        posts.add(post2);

        Post post3 = new Post();
        post3.setHashtags(tags);
        post3.setPostId(3);
        post3.setTitle("Post3");
        post3.setContent("info for post 3");
        post3.setCategoryId(1);
        post3.setIsHidden(Boolean.FALSE);
        post3.setIsApproved(Boolean.TRUE);
        post3.setOriginalTimestamp(timestamp);
        post3.setUpdatedTimestamp(timestamp);
        posts.add(post3);

        Post post4 = new Post();
        post4.setPostId(4);
        post4.setTitle("Post4");
        post4.setContent("info for post 4");
        post4.setCategoryId(2);
        post4.setIsHidden(Boolean.FALSE);
        post4.setIsApproved(Boolean.TRUE);
        post4.setOriginalTimestamp(timestamp);
        post4.setUpdatedTimestamp(timestamp);
        posts.add(post4);

        Post post5 = new Post();
        post5.setPostId(3);
        post5.setTitle("Post3");
        post5.setContent("info for post 3");
        post5.setCategoryId(2);
        post5.setIsHidden(Boolean.FALSE);
        post5.setIsApproved(Boolean.TRUE);
        post5.setOriginalTimestamp(timestamp);
        post5.setUpdatedTimestamp(timestamp);
        posts.add(post5);

        Post post6 = new Post();
        post6.setPostId(3);
        post6.setTitle("Post3");
        post6.setContent("info for post 3");
        post6.setCategoryId(2);
        post6.setIsHidden(Boolean.FALSE);
        post6.setIsApproved(Boolean.TRUE);
        post6.setOriginalTimestamp(timestamp);
        post6.setUpdatedTimestamp(timestamp);
        posts.add(post6);
    }

    @Override
    public void addPost(Post post) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletePost(int postId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updatePost(Post post) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Post getPostById(int postId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Post> getAllPosts() {
        return posts;
    }

    @Override
    public List<Post> getPostByHashtag(int hashtagId) {
        List<Post> newPosts = new ArrayList<>();
        List<Hashtag> tags;
        for (Post p : posts) {
            tags = p.getHashtags();
            for (Hashtag tag : tags) {
                if (tag.getHashtagId() == hashtagId) {
                    newPosts.add(p);
                }
            }
        }
        return newPosts;
    }

    @Override
    public List<Post> getPostByCategory(int categoryId) {
        List<Post> newPosts = new ArrayList<>();
        for (Post p : posts) {
            if (p.getCategoryId() == categoryId) {
                newPosts.add(p);
            }
        }
        return newPosts;
    }
}