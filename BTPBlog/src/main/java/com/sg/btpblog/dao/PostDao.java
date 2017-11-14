package com.sg.btpblog.dao;

import com.sg.btpblog.model.Post;
import java.util.List;

public interface PostDao {

    public void addPost(Post post);

    public void deletePost(int postId);

    public void updatePost(Post post);

    public Post getPostById(int postId);

    public List<Post> getAllPosts();

    public List<Post> getPostByHashtag(int hashtagId);

    public List<Post> getPostByCategory(int categoryId);

}
