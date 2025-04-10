create table users
(
    id       bigserial,
    username varchar(255),
    password varchar(255),
    role     varchar(255),
    primary key (id)
);

insert into users(username, password, role)
values ('admin', '$2y$10$HN8C15Z/au5GHbeveDB44uj5JTg7APqit9xgEv53TTpnRQrYAKRza', 'ADMIN'),
       ('user', '$2y$10$/1FO8OFVKPZshG8a0fMINOkqXqKWv1Z1U8y5dmkBKzSYVeSEcKyva', 'USER'),
       ('guest', '$2y$10$I5lvkcD2e65Fyq.8ZBt8eOlaZ8JYuGyLb6b3ESOnoKyP6.d5gTI3q', 'GUEST');