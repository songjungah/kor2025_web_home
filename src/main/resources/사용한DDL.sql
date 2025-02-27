drop database if exists korweb;
create database korweb;
use korweb;


SELECT * FROM korweb.point;


create table products(
	id int auto_increment ,
    name varchar(100),
    price int ,
    constraint primary key(id)
);