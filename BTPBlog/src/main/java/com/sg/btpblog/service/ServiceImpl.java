package com.sg.btpblog.service;

import com.sg.btpblog.dao.CategoryDao;
import com.sg.btpblog.dao.HashtagDao;
import com.sg.btpblog.dao.PageDao;
import com.sg.btpblog.dao.PostDao;
import com.sg.btpblog.model.Category;
import com.sg.btpblog.model.Hashtag;
import com.sg.btpblog.model.Page;
import com.sg.btpblog.model.Post;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceImpl implements Service {

    private PostDao postDao;
    private HashtagDao hashtagDao;
    private CategoryDao categoryDao;
    private PageDao pageDao;

    public ServiceImpl(PostDao postDao, HashtagDao hashtagDao,
            CategoryDao categoryDao, PageDao pageDao) {
        this.postDao = postDao;
        this.hashtagDao = hashtagDao;
        this.categoryDao = categoryDao;
        this.pageDao = pageDao;
    }

    @Override
    public void addPost(Post post) {
        if (post.getHashtags() != null) {
            List<Hashtag> hashtags = new ArrayList<>();
            for (Hashtag hashtag : post.getHashtags()) {
                String hashtagName = hashtag.getHashtagName().trim();
                if (hashtagDao.getHashtagByName(hashtagName) == null) {
                    hashtagDao.addHashtag(hashtag);
                    hashtags.add(hashtagDao.getHashtagByName(hashtagName));
                } else {
                    hashtags.add(hashtagDao.getHashtagByName(hashtagName));
                }
            }
            post.setHashtags(hashtags);
        }
        postDao.addPost(post);
    }

    @Override
    public Post getPostById(int postId) {
        return setPostHashtags(postDao.getPostById(postId));
    }

    @Override
    public List<Post> getPostByHashtag(int hashtagId) {
        List<Post> posts = sortPostsReverseChronological(
                filterPostsOnVisibility(
                        postDao.getPostByHashtag(hashtagId)));
        for (Post post : posts) {
            post = setPostHashtags(post);
        }
        return posts;
    }

    @Override
    public List<Post> getPostByCategory(int categoryId) {
        List<Post> posts = sortPostsReverseChronological(
                filterPostsOnVisibility(
                        postDao.getPostByCategory(categoryId)));
        for (Post post : posts) {
            post = setPostHashtags(post);
        }
        return posts;
    }

    @Override
    public Hashtag getHashtagById(int hashtagId) {
        return hashtagDao.getHashtagById(hashtagId);
    }

    @Override
    public List<Post> getAllVisiblePosts() {
        return sortPostsReverseChronological(
                filterPostsOnVisibility(
                        getAllPosts()));
    }

    @Override
    public List<Post> getNotApprovedPosts() {
        return sortPostsReverseChronological(
                getAllPosts().stream()
                        .filter(p -> !p.getIsApproved())
                        .collect(Collectors.toList()));
    }

    private List<Post> filterPostsOnVisibility(List<Post> posts) {
        return posts.stream()
                .filter(p -> p.getIsApproved())
                .filter(p -> !p.getIsHidden())
                .collect(Collectors.toList());
    }

    private List<Post> sortPostsReverseChronological(List<Post> posts) {
        List<Post> reverseChronologicalPosts = new ArrayList<>();
        posts.stream()
                .sorted(Comparator.comparing(Post::getOriginalTimestamp))
                .collect(Collectors.toList())
                .forEach(p -> reverseChronologicalPosts.add(0, p));
        return reverseChronologicalPosts;
    }

    private Post setPostHashtags(Post post) {
        post.setHashtags(hashtagDao.getHashtagsByPost(post.getPostId()));
        return post;
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = postDao.getAllPosts();
        for (Post post : posts) {
            post = setPostHashtags(post);
            post.setCategory(categoryDao.getCategoryById(post.getCategoryId()));
        }
        return posts;
    }

    @Override
    public void updatePost(Post post) {
        postDao.updatePost(post);
    }

    @Override
    public void deletePost(int postId) {
        postDao.deletePost(postId);
    }

    @Override
    public void addHashtag(Hashtag hashtag) {
        hashtagDao.addHashtag(hashtag);
    }

    @Override
    public List<Hashtag> getAllHashtags() {
        return hashtagDao.getAllHashtags();
    }

    @Override
    public List<Hashtag> getHashtagsByPostId(Integer postId) {
        List<Hashtag> tags = hashtagDao.getHashtagsByPostId(postId);
        return tags;
    }

    @Override
    public void updateHashtag(Hashtag hashtag) {
        hashtagDao.updateHashtag(hashtag);
    }

    @Override
    public void deleteHashtag(int hashtagId) {
        hashtagDao.deleteHashtag(hashtagId);
    }

    @Override
    public void addCategory(Category category) {
        categoryDao.addCategory(category);
    }

    @Override
    public Category getCategoryById(int categoryId) {
        return categoryDao.getCategoryById(categoryId);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }

    @Override
    public void deleteCategory(int categoryId) {
        categoryDao.deleteCategory(categoryId);
    }

    @Override
    public void addPage(Page page) {
        pageDao.addPage(page);
    }

    @Override
    public Page getPageById(int pageId) {
        return pageDao.getPageById(pageId);
    }

    @Override
    public List<Page> getAllPages() {
        return pageDao.getAllPages();
    }

    @Override
    public void updatePage(Page page) {
        pageDao.updatePage(page);
    }

    @Override
    public void deletePage(int pageId) {
        pageDao.deletePage(pageId);
    }
}
