-- create
create database if not exists resource;
USE resource;
CREATE TABLE IF NOT EXISTS resource ( id  int PRIMARY KEY NOT NULL AUTO_INCREMENT,
 name varchar(500),
 status varchar(500),
 url varchar(500),
 creation_time datetime);
