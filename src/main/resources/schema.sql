drop all objects; -- H2 special statement to drop all objects.

create schema mes;

create table mes.users (
	user_id serial primary key auto_increment,
	login_name varchar(100) unique not null,
	password varchar(100),
	password_hash_type integer default 1,
	hierarchy_node integer default 0,
	enabled char(1) default 'N'
);
