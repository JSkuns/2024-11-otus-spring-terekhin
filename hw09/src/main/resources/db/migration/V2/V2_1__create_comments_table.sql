create table comments
(
    id      bigserial,
    text    varchar(255),
    book_id bigint references books (id) on delete cascade,
    primary key (id)
);

insert into comments(text, book_id)
values ('Text_1', 1),
       ('Text_2', 2),
       ('Text_3', 3),
       ('Text_4', 1);