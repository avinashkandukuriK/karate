delete from users_reporting;

insert into users_reporting(id, email, status, last_login) values (1, 'api.user@example.com', 'ACTIVE', CURRENT_TIMESTAMP());
insert into users_reporting(id, email, status, last_login) values (2, 'qa.user@example.com', 'ACTIVE', CURRENT_TIMESTAMP());
insert into users_reporting(id, email, status, last_login) values (3, 'archived.user@example.com', 'INACTIVE', CURRENT_TIMESTAMP());
