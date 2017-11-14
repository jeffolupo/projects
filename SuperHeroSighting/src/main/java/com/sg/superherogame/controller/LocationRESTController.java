/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherogame.controller;

import com.sg.superherogame.dao.Dao;
import com.sg.superherogame.model.Location;
import com.sg.superherogame.model.Organization;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class LocationRESTController {
    
    private Dao dao;
    
    @Inject
    public LocationRESTController(Dao dao){
        this.dao = dao;
    }
    
    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    @ResponseBody
    public List<Location>getAllLocations(){
       return dao.getAllLocations();
    }
    
    @RequestMapping(value = "/location/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable("id") int id) {
        dao.deleteLocation(id);
    }
    
    @RequestMapping(value = "/location/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLocation(@PathVariable("id") int id, 
            @RequestBody Location location){
        dao.updateLocation(location);
    }
    
    @RequestMapping(value = "/location/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Location getLocation(@PathVariable("id") int id) {
        
        return dao.getLocationByID(id);
    }
    
    @RequestMapping(value = "/location", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createLocation(@RequestBody Location location){
        dao.addLocation(location);
    }

}
