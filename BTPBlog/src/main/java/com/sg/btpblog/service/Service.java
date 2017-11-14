package com.sg.btpblog.service;

import com.sg.btpblog.model.Category;
import com.sg.btpblog.model.Hashtag;
import com.sg.btpblog.model.Page;
import com.sg.btpblog.model.Post;
import java.util.List;

public interface Service {

    void addPost(Post post);

    Post getPostById(int postId);

    List<Post> getPostByHashtag(int hashtagId);

    List<Post> getPostByCategory(int categoryId);

    List<Post> getAllVisiblePosts();

    List<Post> getNotApprovedPosts();

    List<Post> getAllPosts();

    void updatePost(Post post);

    void deletePost(int postId);

    void addHashtag(Hashtag hashtag);

    Hashtag getHashtagById(int hashtagId);

    List<Hashtag> getAllHashtags();
    
    List<Hashtag> getHashtagsByPostId (Integer postId);

    void updateHashtag(Hashtag hashtag);

    void deleteHashtag(int hashtagId);

    void addCategory(Category category);

    Category getCategoryById(int categoryId);

    List<Category> getAllCategories();

    void updateCategory(Category category);

    void deleteCategory(int categoryId);

    void addPage(Page page);

    Page getPageById(int pageId);

    List<Page> getAllPages();

    void updatePage(Page page);

    void deletePage(int pageId);
}
