drop all objects; -- H2 special statement to drop all objects

create table person (
	id serial,
	name varchar(100),
	age int
);

insert into person (name, age) values('bob', 10),('mary', 20);
