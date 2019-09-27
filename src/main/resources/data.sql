insert into mes.department(dept_id, name)
values
(0, 'Department A'),
(1, 'Department B')
;

insert into mes.users(login_name, password, department_id, enabled)
values
('user', 'password', 0, 'Y'),
('bob', 'bobword', 1, 'N'),
('ralph', 'ralph123', null, 'N')
;

insert into mes.user_details(user_name, email)
values
('user', 'user@example.com'),
('bob', 'bob@example.com')
;
