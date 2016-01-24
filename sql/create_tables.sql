drop database if exists graduate;

create database graduate character set utf8;

use graduate;

create table commit (
	id int auto_increment not null primary key ,
	revisionNo varchar(50) not null ,
	author varchar(50) ,
	timeStamp varchar(20) ,
	message varchar(255)
);