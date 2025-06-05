create table authors
(
    id        bigserial,
    full_name varchar(255),
    primary key (id)
);

insert into authors(full_name)
values ('Author_1'),
       ('Author_2'),
       ('Author_3');