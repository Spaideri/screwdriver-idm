create table account (
    id SERIAL primary key,
    username varchar(200) not null,
    password varchar(200) not null
);