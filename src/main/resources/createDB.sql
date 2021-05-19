drop table  if exists city;
create table city (
    id bigserial primary key ,
    name varchar(30) unique not null,
    information varchar
);