/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherogame.dao;

import com.sg.superherogame.model.Attitude;
import com.sg.superherogame.model.Hero;
import com.sg.superherogame.model.Location;
import com.sg.superherogame.model.Organization;
import com.sg.superherogame.model.Sighting;
import com.sg.superherogame.model.SuperPower;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jeffolupo
 */
public interface Dao {
    
    
    //DAO FUNCTIONS FOR ATTITUDE MODEL
    
    
    
    public void addAttitude(Attitude attitude);
    
    public void deleteAttitude(int attitudeID);
    
    public void updateAttitude(Attitude attitude);
    
    public Attitude getAttitudeByID(int attitudeID);
    
    public List<Attitude> getAllAttitudes();
    
    //DAO FUNCTIONS FOR HERO MODEL
    
    
    
    public void addHero(Hero hero);
    
    public void deleteHero(int heroID);
    
    public void updateHero(Hero hero);
    
    public Hero getHeroByID(int id);
    
    public List<Hero>getAllHeroes();
    
    public List<Hero> getHeroesByLocation(int locationId);
    
    public List<Hero> getAllHeroesSightedByDate(java.sql.Date date);
    
    public List<Hero> getAllHeroesInOrganization(int organizationId);
            
    
    //DAO FUNCTIONS FOR LOCATION
    
    
    
    public void addLocation(Location location);
    
    public void deleteLocation(int locationID);
    
    public void updateLocation(Location location);
    
    public Location getLocationByID(int id);
    
    public List<Location>getAllLocations();
    
    public List<Location> getLocationsOfHero(int heroID);  
    
    public List<Location> getAllLocationsSightedByDate(java.sql.Date date);
    
    
    
    
    //DAO FUNCTIONS FOR ORGANIZATION
    
    
    
    public void addOrganization(Organization organization);
    
    public void deleteOrganization(int organizationID);
    
    public void updateOrganization(Organization organization);
    
    public Organization getOrganizationByID(int id);
    
    public List<Organization> getOrganizationHeroBelongsTo(Hero hero);
    
    public List<Organization> getAllOrganizations();
    
    
    //DAO FUNCTIONS FOR SIGHTINGS
    
    
    
    public void addSighting(Sighting sight);
    
    public void deleteSighting(int sightID);
    
    public void updateSighting(Sighting sight);
    
    public Sighting getSightingByID(int id);
    
    public List<Sighting> getAllSightings();
    
    public List<Sighting> getLatestSightings();
    
    
    
    //DAO FUNCTIONS FOR SUPERPOWER
    
    public void addPower(SuperPower power);
    
    public void deletePower(int powerID);
    
    public void updatePower(SuperPower power);
    
    public SuperPower getPowerByID(int id);
    
    public List<SuperPower> getAllSuperPowers();
    
    
    
    // DAO FUNCTIONS FOR BRIDGETABLES
    
    
    
}

