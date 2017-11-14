package com.sg.superherogame.controller;

import com.sg.superherogame.dao.Dao;
import com.sg.superherogame.model.Attitude;
import com.sg.superherogame.model.Hero;
import com.sg.superherogame.model.Organization;
import com.sg.superherogame.model.SuperPower;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jeffolupo
 */
@CrossOrigin
@Controller
public class SuperheroRESTController {
    
    private Dao dao;
    
    @Inject
    public SuperheroRESTController(Dao dao){
        this.dao = dao;
    }
    
    @RequestMapping(value = "/superheroes", method = RequestMethod.GET)
    @ResponseBody
    public List<Hero> getAllHeroes() {
        return dao.getAllHeroes();
    }
    
    @RequestMapping(value = "/superhero/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Hero getAllHero(@PathVariable("id") int id) {
        return dao.getHeroByID(id);
    }
    
    @RequestMapping(value = "/superhero/{id}" , method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHero(@PathVariable("id") int id){
        dao.deleteHero(id);
    }
    
    @RequestMapping(value = "/powers", method = RequestMethod.GET)
    @ResponseBody
    public List<SuperPower> getAllPowers() {
        return dao.getAllSuperPowers();
    }
    
    @RequestMapping(value = "/attitudes", method = RequestMethod.GET)
    @ResponseBody
    public List<Attitude> getAllAttitudes() {
        return dao.getAllAttitudes();
    }
    
    @RequestMapping(value = "/superhero/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateHero(@PathVariable("id") int id, 
            @RequestBody Hero hero) {
        dao.updateHero(hero);
    }
    
    @RequestMapping(value = "/superhero", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void addHero(@RequestBody Hero hero) {
        dao.addHero(hero);
    }
}
