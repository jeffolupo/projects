package com.sg.btpblog.controller;

import com.sg.btpblog.model.Hashtag;
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
public class HashtagRESTController {

    private Service service;

    @Inject

    HashtagRESTController(Service service) {
        this.service = service;
    }

    @RequestMapping(value = "/hashtag/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Hashtag getHashtag(@PathVariable("id") int id) {
        return service.getHashtagById(id);
    }

    @RequestMapping(value = "/hashtag", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createHashtag(@RequestBody Hashtag hashtag) {
        service.addHashtag(hashtag);
    }

    @RequestMapping(value = "/hashtag/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHashtag(@PathVariable("id") int id) {
        service.deleteHashtag(id);
    }

    @RequestMapping(value = "/hashtag/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateHashtag(@RequestBody Hashtag hashtag) {
        service.updateHashtag(hashtag);
    }

    @RequestMapping(value = "/hashtags", method = RequestMethod.GET)
    @ResponseBody
    public List<Hashtag> getAllHashtags() {
        return service.getAllHashtags();
    }

    @RequestMapping(value = "/hashtags/post/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Hashtag> getHashtagsByPostId(@PathVariable("id") int postId) {
        List<Hashtag> tags = service.getHashtagsByPostId(postId);
        return tags;
    }
}
