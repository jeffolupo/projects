/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherogame.test;

import com.sg.superherogame.dao.Dao;
import com.sg.superherogame.model.Attitude;
import com.sg.superherogame.model.Hero;
import com.sg.superherogame.model.Location;
import com.sg.superherogame.model.Organization;
import com.sg.superherogame.model.Sighting;
import com.sg.superherogame.model.SuperPower;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jeffolupo
 */
public class SuperHeroDaoTest {
    
    Dao dao;
    
    
    public SuperHeroDaoTest() {
    }
    
    @Before
    public void setUp() {
        ApplicationContext ctx
            = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("DaoImpl", Dao.class);
        
        DatabaseInitializer.setKnownGoodState();        
    }
    
    @Test
    public void testAddAttitudeAndGetAttitude(){
        Attitude att = new Attitude();
        att.setDescription("Neither");
        
        dao.addAttitude(att);
        
        Attitude newAtt = dao.getAttitudeByID(att.getAttitudeID());
        
        assertEquals(att, newAtt);
    }
    
    @Test
    public void testDeleteAttitude(){
        
        Attitude att = new Attitude();
        att.setDescription("sad");
        
        dao.addAttitude(att);        
        dao.deleteAttitude(att.getAttitudeID());
               
        assertNull(dao.getAttitudeByID(att.getAttitudeID()));
    }
        
    @Test
    public void testUpdateAttitude (){
        
        Attitude att = new Attitude();
        att.setDescription("gloomy");    
        dao.addAttitude(att);
        
        Attitude update = new Attitude();
        update.setDescription("happy");
        update.setAttitudeID(att.getAttitudeID());
        
        dao.updateAttitude(update);
        
        Attitude update2 = dao.getAttitudeByID(update.getAttitudeID());
        
        assertEquals(update.getDescription(), update2.getDescription());
    }
    
    @Test
    public void testAddHeroAndGetHero (){
        
        ArrayList<Organization> orgs = new ArrayList<>();
        
        //testing getOrganizationByID
        
        Organization org1 = dao.getOrganizationByID(1);
        Organization org2 = dao.getOrganizationByID(2);
        
        orgs.add(org1);
        orgs.add(org2);
        
        //testing getPower 
        
        SuperPower sup = dao.getPowerByID(1);
        Attitude att = dao.getAttitudeByID(1);
        
        Hero hero = new Hero();
        
        hero.setAttitude(att);
        hero.setHeroName("MasterCoder");
        hero.setOrganization(orgs);
        hero.setSuperPower(sup);
        
        dao.addHero(hero);
        
        Hero hero2 = dao.getHeroByID(hero.getHeroID());
        
        assertEquals(hero2, hero);
        
        
    }
    
    @Test
    public void testDeleteHero(){
        
        
        dao.deleteHero(1);
        
        assertNull(dao.getHeroByID(1));
    }
    
    @Test
    public void testUpdateHero(){
        
        Hero hero = dao.getHeroByID(1);
        hero.setHeroName("klay");
        dao.updateHero(hero);
        
        assertEquals("klay", dao.getHeroByID(1).getHeroName());
    }
    
    @Test
    public void testGetAllHeros(){
        
        assertEquals(3, dao.getAllHeroes().size());
        
    }
    
    @Test
    public void testGetHeroesByLocation(){
        List<Hero> heroes = dao.getHeroesByLocation(1);
        assertTrue(heroes.size() == 4);
    }
    
    @Test
    public void testGetHeroesSightedByDate() throws ParseException{
        String sDate = "2017-10-22";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(sDate);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        List<Hero> heroes = dao.getAllHeroesSightedByDate(sqlDate);
        
        assertTrue(heroes.size() == 2);
        
    }
    
    @Test
    public void testGetAllHeroesInOrganization(){
       List<Hero> heroes = dao.getAllHeroesInOrganization(2);
       assertTrue(heroes.size() == 2);
    }
    
    
    @Test
    public void testAddPowerAndGetPower (){

        SuperPower power1 = new SuperPower();
        
        power1.setNameOfPower("xray");
        power1.setDescription("see through things");
        
        dao.addPower(power1);
        
        int id = power1.getSuperPowerID();
        
        assertEquals(id, dao.getPowerByID(id).getSuperPowerID());
        
    }
    
    
    @Test
    public void testDeletePower(){
        
        dao.deletePower(1);
        assertEquals(2, dao.getAllSuperPowers().size());
    }
    
    @Test
    public void testUpdatePower(){
    
        SuperPower power = dao.getPowerByID(1);
        
        power.setNameOfPower("Telepathic");
        
        dao.updatePower(power);
        assertEquals("Telepathic",dao.getPowerByID(1).getNameOfPower());
        
    }   
    
    
    @Test
    public void testAddSightingAndGetSighting() throws ParseException{
        
        ArrayList<Hero> heros = new ArrayList<>();
        
        Location location1 = dao.getLocationByID(1);
        
        String sDate = "2017-10-22";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(sDate);
        Timestamp sqlDate = new Timestamp(date.getTime());
  
        
        
        Hero h1 = dao.getHeroByID(1);
        Hero h2 = dao.getHeroByID(2);
        
        heros.add(h1);
        heros.add(h2);
        
        Sighting sight1 = new Sighting();
        
        sight1.setDate(sqlDate);
        sight1.setHeroes(heros);
        sight1.setLocation(location1);
        
        dao.addSighting(sight1);
        
        int id = sight1.getSightingID();
        
        assertEquals(id, dao.getSightingByID(id).getSightingID());     
    }
    
    @Test
    public void testDeleteSightingAndGet(){

        dao.deleteSighting(1); 
        assertEquals(null, dao.getSightingByID(1));
    }
    
    @Test
    public void testUpdateSighting() throws ParseException{
        
        Sighting update = dao.getSightingByID(1);
        
        String sDate = "2017-01-01";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(sDate);
        Timestamp sqlDate = new Timestamp(date.getTime());
        
        update.setDate(sqlDate);
        dao.updateSighting(update);
        assertEquals(sqlDate, dao.getSightingByID(update.getSightingID()).getDate());
    }
    
    @Test
    public void testGetAllSightings(){
        Sighting sight1 = dao.getSightingByID(1);
        Sighting sight2 = dao.getSightingByID(2);
        Sighting sight3 = dao.getSightingByID(3);
        
        List<Sighting>sights = new ArrayList<>();
        
        sights.add(sight1);
        sights.add(sight2);
        sights.add(sight3);
        
//        assertEquals(sights, dao.getAllSightings());
        
        List<Sighting>sights2 = new ArrayList<>();
        sights2 = dao.getAllSightings();
        assertEquals(4, dao.getAllSightings().size());
    }
    
    @Test
    public void testUpdateAndGetOrganization(){
        
        Organization or = dao.getOrganizationByID(1);
        or.setOrganizationName("catniss");
        dao.updateOrganization(or);
        
        assertEquals("catniss", dao.getOrganizationByID(or.getOrganizationID()).getOrganizationName());  
    }
//    @Test
//    public void testAddAndDeleteOrgaization(){
//        
//        Organization or = dao.getOrganizationByID(1);
//        dao.deleteOrganization(1);
//        dao.addOrganization(or);
//        
//        assertTrue(dao.getOrganizationByID(or.getOrganizationID()).getOrganizationID() != 1);
//        assertEquals("Justice League", dao.getOrganizationByID(or.getOrganizationID()).getOrganizationName());    
//    }
    
    @Test
    public void testDeleteOrganization(){
        dao.deleteOrganization(1);
        assertNull(null, dao.getOrganizationByID(1));
    }
    
    @Test
    public void testAddOrganization (){
        
        List<Organization> orgs = new ArrayList<>();
  
        Organization or = dao.getOrganizationByID(1);
        String d = or.getDescription();
        String n = or.getOrganizationName();
        Location l = or.getLocation();
        
        dao.deleteOrganization(1);
        
        Organization nor = new Organization();
        nor.setDescription(d);
        nor.setLocation(l);
        nor.setOrganizationName(n);
        
        dao.addOrganization(nor);
        
        Organization wth = dao.getOrganizationByID(2);
        //orgs = dao.getAllOrganizations();
        
        
        Organization newOrg = dao.getOrganizationByID(nor.getOrganizationID());
        
        assertEquals("Justice League", newOrg.getOrganizationName());
    }
  
    @Test
    public void testGetOrganizationOfHero(){
        
        List<Organization> orgs = dao.getHeroByID(2).getOrganization();
        assertTrue(orgs.size() == 2);
    }
    
    @Test
    public void testGetAllOrganizations(){
        
        assertEquals(3, dao.getAllOrganizations().size());
    }   
    
//    @Test
//    public void testDeletePower(){
//        dao.deletePower(1);
//        
//        assertNull(dao.getPowerByID(1));
//    }
    
    
    
    
    @Test
    public void testAddAndGetLocation(){
        Location loc = new Location();
        loc.setLocationName("My House");
        loc.setAddress("5752 russell ave");
        loc.setLatitude(new BigDecimal(39.2222222));
        loc.setLongitude(new BigDecimal(99.3332212));
        
        dao.addLocation(loc);
        
        assertEquals("My House", dao.getLocationByID(loc.getLocationID()).getLocationName());
        
    }
    
    @Test
    public void testGetLocationsOfHero(){
        List<Location> locs = dao.getLocationsOfHero(1);
        
        assertEquals (2, locs.size());
    }
    
    @Test
    public void testGetAllLocationsSightedByDate() throws ParseException{
        
        String sDate = "2017-10-22";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(sDate);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        List<Location>locs = dao.getAllLocationsSightedByDate(sqlDate);
        
        assertEquals(2, locs.size());
    }
    
    @Test
    public void testGetAllLocations(){    
        assertEquals(3,dao.getAllLocations().size());
    }
    
    
    @Test
    public void testDeleteLocation(){
    
        dao.deleteLocation(1);
        assertEquals(2,dao.getAllLocations().size());
        
    }
    
    @Test
    public void testUpdateLocation(){
    
        Location loc = dao.getLocationByID(1);
        
        loc.setLocationName("Bank");
        
        dao.updateLocation(loc);
        assertEquals("Bank",dao.getLocationByID(1).getLocationName());
        
    }
}

// ask about passing date from java to sql

// ask about add organization method