/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherogame.controller;

import com.sg.superherogame.dao.Dao;
import com.sg.superherogame.model.Location;
import com.sg.superherogame.model.Organization;
import com.sg.superherogame.model.Sighting;
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

/**
 *
 * @author jeffolupo
 */
@CrossOrigin
@Controller
public class SightingRESTController {
    
    private Dao dao;
    
    @Inject
    public SightingRESTController(Dao dao){
        this.dao = dao;
    }    
    
    @RequestMapping(value = "/sightings", method = RequestMethod.GET)
    @ResponseBody
    public List<Sighting> getAllSightings() {
        return dao.getAllSightings();
    }
    
    @RequestMapping(value = "/sighting/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSighting(@PathVariable("id") int id) {
        dao.deleteSighting(id);
    }
    
    @RequestMapping(value = "/sighting/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLocation(@PathVariable("id") int id, 
            @RequestBody Sighting sighting){
        dao.updateSighting(sighting);
    }
    
    @RequestMapping(value = "/sighting", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createLocation(@RequestBody Sighting sighting){
        dao.addSighting(sighting);
    }
    
    @RequestMapping(value = "/latest/sightings", method = RequestMethod.GET)
    @ResponseBody
    public List<Sighting> getLatestSightings() {
        return dao.getLatestSightings();
    }
}
