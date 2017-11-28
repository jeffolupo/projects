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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jeffolupo
 */
public class DaoImpl implements Dao{
    
    
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    
    //PREPARED STATEMENTS FOR ATTITUDE
    
    
    private static final String SQL_INSERT_ATTITUDE            
        = "insert into Attitude (Description) values (?)";
           
    private static final String SQL_UPDATE_ATTITUDE
        = "update Attitude set Description = ? "
        + "where AttitudeID = ?";
    
    private static final String SQL_DELETE_ATTITUDE
        = "delete from Attitude where AttitudeID = ?";
    
    private static final String SQL_SELECT_ATTITUDE
        = "select * from Attitude where AttitudeID = ?";
    
    private static final String SQL_SELECT_ATTITUDE_BY_HERO_ID
        = "select Attitude.attitudeID, attitude.Description " +
          "from attitude " +
          "join Hero " +
          "on Attitude.attitudeID = Hero.attitudeID " +
          "where hero.heroID = ?;";
 
    private static final String SQL_SELECT_ALL_ATTITUDES
        = "select * " +
        "from attitude";
            
    
    // PREPARED STATEMENTS FOR HERO
    
    
    
    private static final String SQL_INSERT_HERO
        = "insert into Hero (HeroName, SuperPowerID, AttitudeID ) values (?, ?, ?)";
    
    private static final String SQL_UPDATE_HERO
        = "update Hero set HeroName = ?, SuperPowerID = ?, AttitudeID = ? "
        + "where HeroID = ?";
            
    private static final String SQL_DELETE_HERO  
        = "delete from Hero where HeroID = ?";
    
    private static final String SQL_DELETE_HERO_WITH_POWER_ID
        = "delete from Hero where SuperPowerId = ?";
            
    private static final String SQL_SELECT_HERO
        = "select * from Hero where HeroID = ?";
    
    private static final String SQL_SELECT_ALL_HEROES
        = "select * from Hero";
    
    private static final String SQL_SELECT_ALL_HEROES_WHERE_SUPERPOWERID
        = "select * from Hero where SuperPowerId = ?";
    
    private static final String SQL_SELECT_HEROES_SIGHTED_AT_SPECIFIC_LOCATION
        = "select h.heroID, h.HeroName, h.SuperPowerId, h.attitudeId " +
          "from hero h " +
          "inner join SightingHero sh " +
          "on h.HeroID = sh.HeroID " +
          "inner join sighting s " +
          "on sh.sightingID = s.sightingID " +
          "inner join location l " +
          "on s.locationID = l.locationID " +
          "where l.LocationID = ?";
    
    private static final String SQL_SELECT_HEROES_IN_SPECIFIC_ORGANIZATION
        = "select h.heroID, h.HeroName, h.SuperPowerId, h.attitudeId " +
          "from Hero h " +
          "inner join HeroOrganization ho " +
          "on h.HeroID = ho.HeroID " +
          "inner join Organization o " +
          "on ho.OrganizationID = o.OrganizationID " +
          "where o.OrganizationID = ?;";
    
    private static final String SQL_SELECT_HEROES_IN_SPECIFIC_SIGHTING
        = "select hero.heroID, hero.HeroName, SuperPower.NameOfPower, attitude.description " +
          "from Hero " +
          "inner join SightingHero " +
          "on hero.heroId = sightingHero.heroId " +
          "inner join sighting " +
          "on sightingHero.sightingID = sighting.sightingID " +
          "inner join superPower " +
          "on hero.superPowerID = superPower.superPowerID " +
          "inner join attitude " +
          "on hero.attitudeID = attitude.attitudeID " +
          "where sighting.sightingID = ?;";
    
    // PREPARED STATEMENTS FOR LOCATION
    
    
    
    private static final String SQL_INSERT_LOCATION
        = "insert into Location (LocationName, Address, Latitude, Longitude) "
        + "values (?, ?, ?, ?)";
    
    private static final String SQL_UPDATE_LOCATION
        = "update Location set LocationName = ?, Address = ?, Latitude = ?, Longitude = ? "
        + "where LocationID = ?";
            
    private static final String SQL_DELETE_LOCATION
        = "delete from Location where LocationID = ?";

    private static final String SQL_SELECT_LOCATION
        = "select * from Location where LocationID = ?";
    
    private static final String SQL_SELECT_ALL_LOCATIONS
        = "select * from Location";
    
    private static final String SQL_SELECT_LOCATIONS_WHERE_SPECIFIC_HERO_WAS_SIGHTED
        = "select l.LocationID, l.LocationName, l.Address, l.Latitude, l.Longitude " +
          "from Location l " +
          "inner join Sighting s " +
          "on l.LocationID = s.LocationID " +
          "inner join SightingHero sh " +
          "on sh.SightingID = s.SightingID " +
          "inner join Hero h " +
          "on sh.HeroID = h.HeroID " +
          "where h.HeroID = ?";
    
    private static final String SQL_SELECT_ALL_LOCATIONS_SIGHTINGS_FOR_SPECIFIC_DATE
        = "select l.LocationID, l.LocationName, l.Address, l.Latitude, l.Longitude " +
          "from Location l " +
          "inner join Sighting s " +
          "on l.LocationID = s.LocationID " +
          "inner join SightingHero sh " +
          "on sh.SightingID = s.SightingID " +
          "inner join Hero h " +
          "on sh.HeroID = h.HeroID " +
          "where `Date` = ?;";
    
    private static final String SQL_SELECT_LOCATION_BY_SIGHTING
        = "select Location.LocationID, LocationName, Address, Latitude, Longitude "
        + "from location " +
          "inner join  sighting " +
          "on location.locationId = sighting.LocationId " +
          "where sightingID = ?;";
    
    private static final String SQL_SELECT_LOCATION_BY_ORGANIZATION
        = "select location.LocationID, location.locationName, location.address, location.latitude, location.longitude " +
          "from Location " +
          "inner join Organization " +
          "on Location.locationID = Organization.locationID " +
          "where Organizationid = ?";
    
    
    //PREPARED STATEMENTS FOR ORGANIZATION
    
    
    private static final String SQL_INSERT_ORGANIZATION
        = "insert into Organization (OrganizationName, LocationID, Description) "
        + "values (?,?,?)";
            
    private static final String SQL_UPDATE_ORGANIZATION
        = "update Organization set OrganizationName = ?, LocationID = ?, Description = ? "
        + "where OrganizationID = ?";
    
    private static final String SQL_DELETE_ORGANIZATION
        = "delete from Organization where OrganizationID = ?";
    
    private static final String SQL_DELETE_ORGANIZATION_BY_LOCATION_ID
        = "delete from Organization where locationID = ?";

    private static final String SQL_SELECT_ORGANIZATION
        = "select * from Organization where OrganizationID = ?";
    
    private static final String SQL_SELECT_ALL_ORGANIZATIONS
        = "select * from Organization";
    
    private static final String SQL_SELECT_ALL_ORGANIZATIONS_WHERE_LOCATIONID
        = "select * from Organization where locationID = ?";
    
    private static final String SQL_SELECT_ORGANIZATIONS_HERO_BELONGS_TO
        = "select o.organizationID, o.organizationName, o.locationID, o.Description " +
          "from organization o " +
          "inner join HeroOrganization ho " +
          "on o.organizationID = ho.organizationID " +
          "inner join Hero h " +
          "on h.heroID = ho.heroID " +
          "where h.heroID = ?;";

    
    //PREPARED STATEMENTS FOR SIGHTINGS
    
    
    private static final String SQL_INSERT_SIGHTING
        = "insert into Sighting (LocationID, `Date`) values ( ?, ?)";    
    
    private static final String SQL_UPDATE_SIGHTING
        = "update Sighting set LocationID = ?, Date = ? "
        + "where SightingID = ?";    
    
    private static final String SQL_DELETE_SIGHTING
        = "delete from Sighting where SightingID = ?";

    private static final String SQL_SELECT_SIGHTING
        = "select * from Sighting where SightingID = ?";
    
    private static final String SQL_SELECT_ALL_SIGHTINGS
        = "select * from Sighting";
    
    private static final String SQL_SELECT_LATEST_9_SIGHTINGS
        = "SELECT * FROM sighting ORDER BY sightingId desc limit 9;";
    
    private static final String SQL_SELECT_ALL_SIGHTINGS_WHERE_LOCATIONID
        = "select * from Sighting where locationId = ?;";
            
    private static final String SQL_SELECT_ALL_HEROES_SIGHTINGS_FOR_SPECIFIC_DATE
        = "select h.heroID, h.HeroName, h.SuperPowerId, h.attitudeId " +
          "from Location l " +
          "inner join Sighting s " +
          "on l.LocationID = s.LocationID " +
          "inner join SightingHero sh " +
          "on sh.SightingID = s.SightingID " +
          "inner join Hero h " +
          "on sh.HeroID = h.HeroID " +
          "where `Date` = ?;";
 
    
    
    //PREPARED STATEMENTS FOR POWER
    
    
    private static final String SQL_INSERT_POWER
        = "insert into SuperPower (NameOfPower, Description) values (?, ?)";
    
    private static final String SQL_UPDATE_POWER
        = "update SuperPower set NameOfPower = ?, Description = ? "
        + "where SuperPowerID = ?";
    
    private static final String SQL_DELETE_POWER
        = "delete from SuperPower where SuperPowerID = ?";
    
    private static final String SQL_SELECT_POWER
        = "select * from SuperPower where SuperPowerID = ?";
    
    private static final String SQL_SELECT_ALL_POWERS
        = "select * from SuperPower";
    
    private static final String SQL_SELECT_SUPERPOWER_BY_HERO_ID
        = "select SuperPower.SuperPowerID, SuperPower.NameOfPower, SuperPower.Description " +
          "from superpower " +
          "join Hero " +
          "on superPower.superPowerID = Hero.SuperPowerID " +
          "where hero.heroID = ?;";
            
    
    
    //PREPARED STATEMENTS FOR SIGHTING-HERO
    
    
    private static final String SQL_INSERT_SIGHTING_HERO
        = "insert into SightingHero (SightingID, HeroID) values (?, ?)";
    
    private static final String SQL_DELETE_SIGHTING_HERO_WITH_SIGHTING_ID
        = "delete from SightingHero where SightingID = ?";
    
    private static final String SQL_DELETE_SIGHTING_HERO_WITH_HERO_ID
        = "delete from SightingHero where heroID = ?";
    
    
    // PREPARED STATEMENTS FOR HERO-ORGANIZATION
    
    
    private static final String SQL_INSERT_HERO_ORGANIZATION
        = "insert into HeroOrganization (HeroID, OrganizationID) values(?, ?)";
    
    private static final String SQL_SELECT_HERO_ORGANIZATION_HERO_ID
        = " select o.organizationID, o.organizationName " +
          "from organization o " +
          "inner join HeroOrganization ho " +
          "on o.organizationID = ho.organizationID " +
          "where heroID = ?;";
    
    private static final String SQL_DELETE_HERO_ORGANIZATION_WITH_HERO_ID
        = "delete from HeroOrganization where HeroID = ?";
    
    
    private static final String SQL_DELETE_HERO_ORGANIZATION_WITH_ORGANIZATION_ID
        = "delete from HeroOrganization where OrganizationID = ?";
    
    //DAO ATTITUDE FUNCTIONS
    
  

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addAttitude(Attitude attitude) {
        
        jdbcTemplate.update(SQL_INSERT_ATTITUDE,
                attitude.getDescription());
        
        int attitudeId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        
        attitude.setAttitudeID(attitudeId);
    }

    @Override
    public void deleteAttitude(int attitudeID) {
        jdbcTemplate.update(SQL_DELETE_ATTITUDE, attitudeID);
    }

    @Override
    public void updateAttitude(Attitude attitude) {
        jdbcTemplate.update(SQL_UPDATE_ATTITUDE,
                attitude.getDescription(),
                attitude.getAttitudeID());
    }

    @Override
    public Attitude getAttitudeByID(int attitudeID) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ATTITUDE, new AttitudeMapper(), attitudeID);
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }
    
    @Override
    public List<Attitude> getAllAttitudes(){
        return jdbcTemplate.query(SQL_SELECT_ALL_ATTITUDES, new AttitudeMapper());    
    }
    
    
    
    // DAO HERO FUNCTIONS
    
    
    

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addHero(Hero hero) {
        jdbcTemplate.update(SQL_INSERT_HERO,
                hero.getHeroName(),
                hero.getSuperPower().getSuperPowerID(),
                hero.getAttitude().getAttitudeID());
        
        hero.setHeroID(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));
        
        insertHeroOrganization(hero);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteHero(int heroID) {
        
        jdbcTemplate.update(SQL_DELETE_SIGHTING_HERO_WITH_HERO_ID, heroID);
        jdbcTemplate.update(SQL_DELETE_HERO_ORGANIZATION_WITH_HERO_ID, heroID);
        jdbcTemplate.update(SQL_DELETE_HERO, heroID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateHero(Hero hero) {
        jdbcTemplate.update(SQL_UPDATE_HERO, 
                hero.getHeroName(),
                hero.getSuperPower().getSuperPowerID(),
                hero.getAttitude().getAttitudeID(),
                hero.getHeroID());
        
        jdbcTemplate.update(SQL_DELETE_HERO_ORGANIZATION_WITH_HERO_ID, hero.getHeroID());
        insertHeroOrganization(hero);
        
        jdbcTemplate.update(SQL_DELETE_SIGHTING_HERO_WITH_HERO_ID, hero.getHeroID());
    
    }

    @Override
    public Hero getHeroByID(int id) {
        
        try{
            Hero hero = jdbcTemplate.queryForObject(SQL_SELECT_HERO, 
                    new HeroMapper(),
                    id);

            hero.setAttitude(findAttitudeForHero(hero));
            hero.setSuperPower(findSuperPowerForHero(hero));
            hero.setOrganization(findOrganizationsForHero(hero));
            hero.setOrganization(associateLocationWithOrganization(hero.getOrganization()));
            return hero;

        } catch(EmptyResultDataAccessException ex){
            return null;
        }
        
    }
    
    @Override
    public List<Hero> getAllHeroes(){
        List<Hero> heroList = jdbcTemplate.query(SQL_SELECT_ALL_HEROES,
                new HeroMapper());
        return associateSuperPowerAndAttitudeAndOrganizationsWithHero(heroList);
    }

    @Override
    public List<Hero> getHeroesByLocation(int locationid) {
        List<Hero> heroList = jdbcTemplate.query(SQL_SELECT_HEROES_SIGHTED_AT_SPECIFIC_LOCATION,
                new HeroMapper(),
                locationid);
        return associateSuperPowerAndAttitudeAndOrganizationsWithHero(heroList);
    }

    @Override
    public List<Hero> getAllHeroesSightedByDate(java.sql.Date date) {
        List<Hero> heroList = jdbcTemplate.query(SQL_SELECT_ALL_HEROES_SIGHTINGS_FOR_SPECIFIC_DATE,
                new HeroMapper(),
                date);
        return associateSuperPowerAndAttitudeAndOrganizationsWithHero(heroList);
    }

    @Override
    public List<Hero> getAllHeroesInOrganization(int organizationId) {
        List<Hero> heroList = jdbcTemplate.query(SQL_SELECT_HEROES_IN_SPECIFIC_ORGANIZATION,
                new HeroMapper(),
                organizationId);
        return associateSuperPowerAndAttitudeAndOrganizationsWithHero(heroList);
    }
    
//    @Override
//    public List<Hero> getAllHeroes(){
//
//    }
    
    
    
    //DAO LOCATION FUNCTIONS
    
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addLocation(Location location) {
        jdbcTemplate.update(SQL_INSERT_LOCATION, 
                location.getLocationName(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());
        
        int locationId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        
        location.setLocationID(locationId);
        
    }

    @Override
    public void deleteLocation(int locationID) {
        List<Organization> orgs = jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS_WHERE_LOCATIONID, 
                new OrganizationMapper(),
                locationID);
        List<Sighting> sights = jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS_WHERE_LOCATIONID,
                new SightingMapper(),
                locationID);
        for(Organization o : orgs){
            deleteOrganization(o.getOrganizationID());
        }
        for(Sighting s : sights){
            deleteSighting(s.getSightingID());
        }
        jdbcTemplate.update(SQL_DELETE_LOCATION, locationID);
    }
    
//     @Override
//    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//    public void updateHero(Hero hero) {
//        jdbcTemplate.update(SQL_UPDATE_HERO, 
//                hero.getHeroName(),
//                hero.getSuperPower().getSuperPowerID(),
//                hero.getAttitude().getAttitudeID(),
//                hero.getHeroID());
//        
//        jdbcTemplate.update(SQL_DELETE_HERO_ORGANIZATION_WITH_HERO_ID, hero.getHeroID());
//        insertHeroOrganization(hero);
//        
//        jdbcTemplate.update(SQL_DELETE_SIGHTING_HERO_WITH_HERO_ID, hero.getHeroID());
//    
//    }

    @Override
    public void updateLocation(Location location) {
        jdbcTemplate.update(SQL_UPDATE_LOCATION,
                location.getLocationName(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationID());
    }

    @Override
    public Location getLocationByID(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION, new LocationMapper(), id);
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }
    
    @Override
    public List<Location> getAllLocations(){
        List<Location>locs = jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS,
                new LocationMapper());
        return locs;
    }

    @Override
    public List<Location> getLocationsOfHero(int heroID) {
        List<Location> locationList = jdbcTemplate.query(SQL_SELECT_LOCATIONS_WHERE_SPECIFIC_HERO_WAS_SIGHTED,
                new LocationMapper(),
                heroID);
        return locationList;
        
        
    }

    @Override
    public List<Location> getAllLocationsSightedByDate(java.sql.Date date) {
        List<Location> locationList = jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS_SIGHTINGS_FOR_SPECIFIC_DATE,
                new LocationMapper(),
                date);
        return locationList;
    }
    
//    @Override
//    public List<Location> getAllLocations(){
//    
//    }
    
    
    
    //  DAO ORGANIZATION FUNCTIONS
    
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//    public void addLocation(Location location) {
//        jdbcTemplate.update(SQL_INSERT_LOCATION, 
//                location.getLocationName(),
//                location.getAddress(),
//                location.getLatitude(),
//                location.getLongitude());
//        
//        int locationId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
//        
//        location.setLocationID(locationId);
//        
//    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addOrganization(Organization organization) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getLocation().getLocationID(),
                organization.getDescription());
        
        organization.setOrganizationID(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));
        
        
    }

    @Override
    public void deleteOrganization(int organizationID) {
        jdbcTemplate.update(SQL_DELETE_HERO_ORGANIZATION_WITH_ORGANIZATION_ID, organizationID);
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organizationID);
    }

    @Override
    public void updateOrganization(Organization organization) {       
        
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getLocation().getLocationID(),
                organization.getDescription(),
                organization.getOrganizationID());
        
        jdbcTemplate.update(SQL_DELETE_HERO_ORGANIZATION_WITH_ORGANIZATION_ID, 
                organization.getOrganizationID());
        
    }

    @Override
    public Organization getOrganizationByID(int id) {
        try {
            Organization or = jdbcTemplate.queryForObject(SQL_SELECT_ORGANIZATION, 
                    new OrganizationMapper(), 
                    id);
            or.setLocation(findLocationForOrganization(or));
            return or;
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }
    
    @Override
    public List <Organization> getAllOrganizations(){
        List<Organization> orgs = jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS,
                new OrganizationMapper());
        return associateLocationWithOrganization(orgs);     
    }
    
    public List<Organization> getAllOrganizationsWhere(){
        List<Organization> orgs = jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS_WHERE_LOCATIONID,
                new OrganizationMapper());
        return associateLocationWithOrganization(orgs);
    }

    @Override
    public List<Organization> getOrganizationHeroBelongsTo(Hero hero) {
        
        return hero.getOrganization();   
    }
    
    
    
    // DAO SIGHTING FUNCTIONS
    
    
    

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSighting(Sighting sight) {
        jdbcTemplate.update(SQL_INSERT_SIGHTING,
                sight.getLocation().getLocationID(),
                sight.getDate());
        sight.setSightingID(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));
        
        insertSightingHero(sight);
        
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSighting(int sightID) {
        jdbcTemplate.update(SQL_DELETE_SIGHTING_HERO_WITH_SIGHTING_ID, sightID);
        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSighting(Sighting sight) {
        jdbcTemplate.update(SQL_UPDATE_SIGHTING,
                sight.getLocation().getLocationID(),
                sight.getDate(),
                sight.getSightingID());
        jdbcTemplate.update(SQL_DELETE_SIGHTING_HERO_WITH_SIGHTING_ID, sight.getSightingID());
        
        insertSightingHero(sight);
                
    }

    @Override
    public Sighting getSightingByID(int id) {
        try{
            Sighting sight = jdbcTemplate.queryForObject(SQL_SELECT_SIGHTING,
                    new SightingMapper(),
                    id);
            sight.setLocation(findLocationForSighting(sight));
            sight.setHeroes(findHeroesForSighting(sight));
            sight.setHeroes(associateSuperPowerAndAttitudeAndOrganizationsWithHero(sight.getHeroes()));
            
            List<Hero> heroes = sight.getHeroes();
            
            for(Hero h : heroes){
                h.setOrganization(associateLocationWithOrganization(h.getOrganization()));
            }
            sight.setHeroes(heroes);
            
            
            return sight;
        } catch (EmptyResultDataAccessException ex) {
            return null;   
        }
    }
    
    @Override
    public List<Sighting> getAllSightings(){
        try{
            List<Sighting> sights = jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS,
                    new SightingMapper());
            for (Sighting sight: sights){
                sight.setLocation(findLocationForSighting(sight));
                sight.setHeroes(findHeroesForSighting(sight));
                sight.setHeroes(associateSuperPowerAndAttitudeAndOrganizationsWithHero(sight.getHeroes()));

                List<Hero> heroes = sight.getHeroes();
            
                for(Hero h : heroes){
                    h.setOrganization(associateLocationWithOrganization(h.getOrganization()));
                }
                
                sight.setHeroes(heroes);
            }           
            return sights;            
        } catch(EmptyResultDataAccessException ex){
            return null;
        }
    }
    
    public List<Sighting> getLatestSightings(){
        try{
            List<Sighting> sights = jdbcTemplate.query(SQL_SELECT_LATEST_9_SIGHTINGS, new SightingMapper());
            for (Sighting sight : sights){
                sight.setLocation(findLocationForSighting(sight));
                sight.setHeroes(findHeroesForSighting(sight));
                sight.setHeroes(associateSuperPowerAndAttitudeAndOrganizationsWithHero(sight.getHeroes()));

                List<Hero> heroes = sight.getHeroes();
            
                for(Hero h : heroes){
                    h.setOrganization(associateLocationWithOrganization(h.getOrganization()));
                }
                
                sight.setHeroes(heroes);
            }           
            return sights;            
        } catch(EmptyResultDataAccessException ex){
            return null;
        }
    }
    
    public List<Sighting> getAllSightingsWhere(){
        try{
            List<Sighting> sights = jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS_WHERE_LOCATIONID,
                    new SightingMapper());
            for (Sighting sight: sights){
                sight.setLocation(findLocationForSighting(sight));
                sight.setHeroes(findHeroesForSighting(sight));
                sight.setHeroes(associateSuperPowerAndAttitudeAndOrganizationsWithHero(sight.getHeroes()));

                List<Hero> heroes = sight.getHeroes();
            
                for(Hero h : heroes){
                    h.setOrganization(associateLocationWithOrganization(h.getOrganization()));
                }
                
                sight.setHeroes(heroes);
            }           
            return sights;            
        } catch(EmptyResultDataAccessException ex){
            return null;
        }
    }

    
    // DAO POWER FUNCTIONS
    
    

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addPower(SuperPower power) {
        jdbcTemplate.update(SQL_INSERT_POWER, 
                power.getNameOfPower(),
                power.getDescription());
        
        int powerId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        
        power.setSuperPowerID(powerId);
    }

    @Override
    public void deletePower(int powerID) {
        
        List<Hero> heroes = jdbcTemplate.query(SQL_SELECT_ALL_HEROES_WHERE_SUPERPOWERID,
                new HeroMapper(),
                powerID);
        
        for (Hero h : heroes){
            deleteHero(h.getHeroID());
        }
        jdbcTemplate.update(SQL_DELETE_POWER, powerID);
    }

    @Override
    public void updatePower(SuperPower power) {
        jdbcTemplate.update(SQL_UPDATE_POWER,
                power.getNameOfPower(),
                power.getDescription(),
                power.getSuperPowerID());
    }

    @Override
    public SuperPower getPowerByID(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_POWER, new SuperPowerMapper(), id);
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }
    
    @Override
    public List<SuperPower> getAllSuperPowers(){
        try {
            return jdbcTemplate.query(SQL_SELECT_ALL_POWERS, 
                    new SuperPowerMapper());
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }
    
    
    
    // ****************HELPER METHODS********************************
    
    
    
    //helper methods to complete bridge table
    
    private void insertSightingHero (Sighting sighting){
        final int sightingId = sighting.getSightingID();
        final List<Hero> heroes = sighting.getHeroes();
        
        for (Hero currentHero : heroes) {
            jdbcTemplate.update(SQL_INSERT_SIGHTING_HERO, 
                    sightingId,
                    currentHero.getHeroID());
        }
    } 
    
    private void insertHeroOrganization (Hero hero){
        final int heroId = hero.getHeroID();
        final List<Organization> organizations = hero.getOrganization();
        
        for (Organization currentOrganization : organizations){
            jdbcTemplate.update(SQL_INSERT_HERO_ORGANIZATION,
                    heroId,
                    currentOrganization.getOrganizationID());
        }
    }
    
    //Helper methods to complete Sighting Object
    
    private List<Hero> findHeroesForSighting(Sighting sighting){
        return jdbcTemplate.query(SQL_SELECT_HEROES_IN_SPECIFIC_SIGHTING,
                new HeroMapper(),
                sighting.getSightingID());
    }
    
    private Location findLocationForSighting (Sighting sighting){
        return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_SIGHTING,
                new LocationMapper(),
                sighting.getSightingID());
    }
    
    private List<Sighting>
            associateLocationAndHeroesWithSighting(List<Sighting> sightingList){
        for (Sighting currentSighting : sightingList){
            currentSighting.setHeroes(findHeroesForSighting(currentSighting));
            currentSighting.setLocation(findLocationForSighting(currentSighting));
        }
        
        return sightingList;
    }
            
    //Helper methods to complete Organization Object
    
    private Location findLocationForOrganization(Organization organization){
        return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_ORGANIZATION,
                new LocationMapper(),
                organization.getOrganizationID());
    } 
    
    private List<Organization>
            associateLocationWithOrganization(List<Organization>organizationList){
                
        for (Organization currentOrganization : organizationList){
            currentOrganization.setLocation(findLocationForOrganization(currentOrganization));
        }
        
        return organizationList;
    }
    
    //Helper methods to complete Hero
            
    private SuperPower findSuperPowerForHero(Hero hero){
        return jdbcTemplate.queryForObject(SQL_SELECT_SUPERPOWER_BY_HERO_ID, 
                new SuperPowerMapper(),
                hero.getHeroID());
    }
    
    private Attitude findAttitudeForHero(Hero hero){
        return jdbcTemplate.queryForObject(SQL_SELECT_ATTITUDE_BY_HERO_ID, 
                new AttitudeMapper(),
                hero.getHeroID());
    }
    
    private List<Organization> findOrganizationsForHero(Hero hero){
        return jdbcTemplate.query(SQL_SELECT_ORGANIZATIONS_HERO_BELONGS_TO,
                new OrganizationMapper(),
                hero.getHeroID());
    }
    
    
    private List<Hero>
            associateSuperPowerAndAttitudeAndOrganizationsWithHero(List<Hero> heroList){
        
        for(Hero currentHero: heroList){
            currentHero.setAttitude(findAttitudeForHero(currentHero));
            currentHero.setSuperPower(findSuperPowerForHero(currentHero));
            currentHero.setOrganization(findOrganizationsForHero(currentHero));
        }
        
        return heroList;
    }
            
    
    // MAPPERS
    
    
    
    
    private static final class AttitudeMapper implements RowMapper<Attitude> {

        @Override
        public Attitude mapRow(ResultSet rs, int i) throws SQLException {
            Attitude at = new Attitude();
            at.setAttitudeID(rs.getInt("AttitudeID"));
            at.setDescription(rs.getString("Description"));
            return at;
        }
    }
        
    private static final class HeroMapper implements RowMapper<Hero> {
        
        @Override
        public Hero mapRow(ResultSet rs, int i) throws SQLException{
            Hero he = new Hero();
            he.setHeroID(rs.getInt("HeroID"));
            he.setHeroName(rs.getString("HeroName"));
            return he;
           
        }

    }
    
    private static final class LocationMapper implements RowMapper<Location> {
        
        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException{
            Location lo = new Location();
            lo.setLocationID(rs.getInt("LocationID"));
            lo.setLocationName(rs.getString("LocationName"));
            lo.setAddress(rs.getString("Address"));
            lo.setLatitude(rs.getBigDecimal("Latitude"));
            lo.setLongitude(rs.getBigDecimal("Longitude"));
            return lo;
        }
    }
    
    private static final class OrganizationMapper implements RowMapper<Organization> {
        
        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException{
            Organization or = new Organization();
            or.setOrganizationID(rs.getInt("OrganizationID"));
            or.setOrganizationName(rs.getString("OrganizationName"));
            or.setDescription(rs.getString("Description"));
            return or;
        }
    }
    
    private static final class SightingMapper implements RowMapper<Sighting> {
        
        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException{
            Sighting si = new Sighting();
            si.setSightingID(rs.getInt("SightingID"));
            si.setDate(rs.getTimestamp("Date"));
            return si;
        }
    }
    
    private static final class SuperPowerMapper implements RowMapper<SuperPower> {
        
        @Override
        public SuperPower mapRow(ResultSet rs, int i) throws SQLException{
            SuperPower sp = new SuperPower();
            sp.setSuperPowerID(rs.getInt("SuperPowerID"));
            sp.setNameOfPower(rs.getString("NameOfPower"));
            sp.setDescription(rs.getString("Description"));
            return sp;
        }
    }
    
}
