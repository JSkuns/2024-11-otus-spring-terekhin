CREATE TABLE IF NOT EXISTS tools
(
    id              BIGSERIAL PRIMARY KEY,
    tool_type       VARCHAR(255) NOT NULL,
    diameter        DECIMAL(10, 2),
    length          DECIMAL(10, 2),
    manufacturer    VARCHAR(255)
);

INSERT INTO tools (tool_type, diameter, length, manufacturer)
VALUES ('Сверло', 6.00, 100.00, 'Bosch'),
       ('Метчик', 8.00, 50.00, 'Metabo'),
       ('Фреза', 10.00, 150.00, 'Makita'),
       ('Напильник', 12.00, 200.00, 'Stanley'),
       ('Шлифовальный круг', 150.00, 5.00, 'Hitachi'),
       ('Щетка металлическая', null, 160.00, 'Black&Decker'),
       ('Отвертка', 5.00, 150.00, 'Craftsman'),
       ('Ножовка', null, 500.00, 'Klein Tools'),
       ('Шуруповерт', null, 200.00, 'Intertool');