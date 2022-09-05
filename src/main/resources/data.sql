INSERT INTO roles (id, role_name, description) VALUES (1, 'ROLE_ADMIN', 'Administrator');
INSERT INTO roles (id, role_name, description) VALUES (2, 'ROLE_STANDARD', 'Standard');

insert into Users (name, email, password)values ( 'usuario Juan Perez 1','usuario1@server.com','pass1' );
insert into Users (name, email, password)values ( 'usuario Juan Perez 2','usuario2@server.com','pass2' );
insert into Users (name, email, password)values ( 'usuario Juan Perez 3','usuario3@server.com','pass3' );
insert into Users (name, email, password)values ( 'Juan Rodriguez','juan@rodriguez.org','hunter2' );

insert into Phones(country_code, city_code, number, user_id )
values ( '57', '1', '1234567', (select id from users where email = 'juan@rodriguez.org') )
