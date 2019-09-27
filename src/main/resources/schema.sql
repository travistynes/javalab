drop all objects; -- H2 special statement to drop all objects.

create schema mes;

create table mes.users (
	login_name varchar(100) primary key,
	password varchar(100),
	password_hash_type integer default 1,
	hierarchy_node integer default 0,
	department_id integer default 0,
	enabled char(1) default 'N'
);

create table mes.user_details (
	user_id serial primary key auto_increment,
	user_name varchar(100) unique not null,
	email varchar(100)
);

create table mes.department (
	dept_id serial primary key,
	name varchar(50)
);
