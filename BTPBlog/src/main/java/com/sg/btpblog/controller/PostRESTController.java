package com.sg.btpblog.controller;

import com.sg.btpblog.model.Post;
import com.sg.btpblog.service.Service;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@CrossOrigin
@Controller
public class PostRESTController {

    @Inject
    private Service service;

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createPost(@RequestBody Post post) {
        service.addPost(post);
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Post getPostById(@PathVariable("id") int postId) {
        return service.getPostById(postId);
    }

    @RequestMapping(value = "/posts/hashtag/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> getPostByHashtag(@PathVariable("id") int hashtagId) {
        return service.getPostByHashtag(hashtagId);
    }

    @RequestMapping(value = "/posts/category/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> getPostByCategory(@PathVariable("id") int categoryId) {
        return service.getPostByCategory(categoryId);
    }

    @RequestMapping(value = "/posts/visible", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> getAllVisiblePosts() {
        return service.getAllVisiblePosts();
    }

    @RequestMapping(value = "/posts/notapproved", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> getNotApprovedPosts() {
        return service.getNotApprovedPosts();
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> getAllPosts() {
        return service.getAllPosts();
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@PathVariable("id") int postId, @RequestBody Post post) {
        service.updatePost(post);
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") int id) {
        service.deletePost(id);
    }
}
