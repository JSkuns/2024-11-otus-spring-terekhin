create table genres
(
    id   bigserial,
    name varchar(255),
    primary key (id)
);

insert into genres(name)
values ('Genre_1'),
       ('Genre_2'),
       ('Genre_3');