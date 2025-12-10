delete from orders;
delete from users;

insert into users(id, email, status, role) values (1, 'api.user@example.com', 'ACTIVE', 'ADMIN');
insert into users(id, email, status, role) values (2, 'qa.user@example.com', 'ACTIVE', 'ANALYST');

insert into orders(id, user_id, total, channel) values ('ord-1', 1, 25.50, 'WEB');
insert into orders(id, user_id, total, channel) values ('ord-2', 1, 199.99, 'STORE');
