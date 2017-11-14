package com.sg.btpblog.controller;

import com.sg.btpblog.model.Page;
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
public class PageRESTController {

    private Service service;

    @Inject
    PageRESTController(Service service) {
        this.service = service;
    }

    @RequestMapping(value = "/page/public/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Page getPage(@PathVariable("id") int id) {
        return service.getPageById(id);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createPage(@RequestBody Page page) {
        service.addPage(page);
    }

    @RequestMapping(value = "/page/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePage(@PathVariable("id") int id) {
        service.deletePage(id);
    }

    @RequestMapping(value = "/page/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePage(@PathVariable("id") int id, @RequestBody Page page) throws UpdateIntegrityException {

        if (id != page.getPageId()) {
            throw new UpdateIntegrityException(" Id on URL must match Id in submitted data.");
        }

        service.updatePage(page);
    }

    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    @ResponseBody
    public List<Page> getAllPages() {
        return service.getAllPages();
    }
}
