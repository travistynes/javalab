drop all objects; -- H2 special statement to drop all objects

create table person (
	id serial,
	name varchar(100),
	age int
);

create table pet (
	id serial,
	owner_id int,
	name varchar(100)
);

insert into person (id, name, age) values(1, 'bob', 10),(2, 'mary', 20);
insert into pet (owner_id, name) values(2, 'Fluffy'),(2, 'Peanut');
