create table if not exists users (
    id bigint primary key,
    email varchar(255),
    status varchar(64),
    role varchar(64)
);

create table if not exists orders (
    id varchar(64) primary key,
    user_id bigint,
    total decimal(10,2),
    channel varchar(32)
);
