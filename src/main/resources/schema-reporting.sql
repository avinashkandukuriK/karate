create table if not exists users_reporting (
    id bigint primary key,
    email varchar(255),
    status varchar(64),
    last_login timestamp
);
