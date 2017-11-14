
delete from HeroOrganization;
delete from SightingHero;
delete from Sighting;
delete from Hero;
delete from Organization;
delete from Location;
delete from Attitude;
delete from SuperPower;

insert into SuperPower (SuperPowerID,NameOfPower, Description) values (1,'Teleport', 'Can teleport anywhere');
insert into SuperPower (SuperPowerID,NameOfPower, Description) values (2,'Power Ring', 'Can create anything imanageable');
insert into SuperPower (SuperPowerID,NameOfPower, Description) values (3,'Super Healing', 'Can heal from any injury within minutes');

insert into Attitude (AttitudeId, Description) values (1, 'Good');
insert into Attitude (AttitudeId, Description) values (2, 'Evil');

insert into Location (LocationID, LocationName, Address, Latitude, Longitude) values (1,'Hall of Justice', '2347 Mathias Rd', '100.2223335','89.2223341');
insert into Location (LocationID, LocationName, Address, Latitude, Longitude) values (2,'Cheese Cake Factory', '5750 Penn Ave', '90.2223335','50.2223341');
insert into Location (LocationID, LocationName, Address, Latitude, Longitude) values (3,'Xaviers School', '1818 Freemont Ave', '74.2223335','21.2223341');

insert into Organization (OrganizationID, OrganizationName, LocationID, Description) values (1,'Justice League', '1', 'Justice League Head Quarters');
insert into Organization (OrganizationID, OrganizationName, LocationID, Description) values (2,'X-Men', '3', 'X-Men Head Quarters and School for the gifted');
insert into Organization (OrganizationID, OrganizationName, LocationID, Description) values (3,'BALLERS', '2', 'The Most Ballin Super Heroes');

insert into Hero (HeroID,HeroName, SuperPowerID, AttitudeID) values (1,'NightCrawler', '1', '1');
insert into Hero (HeroID,HeroName, SuperPowerID, AttitudeID) values (2,'Green Lantern', '2', '1');
insert into Hero (HeroID,HeroName, SuperPowerID, AttitudeID) values (3,'Wolverine', '3', '1');

insert into Sighting (SightingID, LocationID, `Date`) values (1,'1', '2017/10/22');
insert into Sighting (SightingID, LocationID, `Date`) values (2,'1', '2017/10/23');
insert into Sighting (SightingID, LocationID, `Date`) values (3,'2', '2017/10/23');
insert into Sighting (SightingID, LocationID, `Date`) values (4,'3', '2017/10/23');

insert into SightingHero (SightingID, HeroID) values ('1','1');
insert into SightingHero (SightingID, HeroID) values ('1','2');
insert into SightingHero (SightingID, HeroID) values ('2','2');
insert into SightingHero (SightingID, HeroID) values ('2','3');
insert into SightingHero (SightingID, HeroID) values ('3','1');
insert into SightingHero (SightingID, HeroID) values ('3','3');

insert into HeroOrganization (HeroID, OrganizationID) values ('1','2');
insert into HeroOrganization (HeroID, OrganizationID) values ('1','3');
insert into HeroOrganization (HeroID, OrganizationID) values ('2','1');
insert into HeroOrganization (HeroID, OrganizationID) values ('2','3');
insert into HeroOrganization (HeroID, OrganizationID) values ('3','2');
