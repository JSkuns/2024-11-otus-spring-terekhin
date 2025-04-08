create table users
(
    id       bigserial,
    username varchar(255),
    password varchar(255),
    role     varchar(255),
    primary key (id)
);

insert into users(username, password, role)
values ('admin', 'admin', 'ROLE_ADMIN'),
       ('user', 'user', 'ROLE_USER'),
       ('guest', 'guest', 'ROLE_GUEST');