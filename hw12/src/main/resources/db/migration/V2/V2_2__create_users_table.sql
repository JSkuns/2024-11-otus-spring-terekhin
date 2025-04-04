create table users
(
    id       bigserial,
    username varchar(255),
    password varchar(255),
    primary key (id)
);

insert into users(username, password)
values ('User_1', '123'),
       ('User_2', '234'),
       ('User_3', '345'),
       ('User_4', '456');