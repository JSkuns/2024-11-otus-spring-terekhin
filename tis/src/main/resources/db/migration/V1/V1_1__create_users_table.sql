CREATE TABLE IF NOT EXISTS users
(
    id              BIGSERIAL PRIMARY KEY,
    username        VARCHAR(255) NOT NULL,
    password        VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id         INT NOT NULL REFERENCES users(id),
    role_id         INT NOT NULL REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'), ('ROLE_USER'), ('ROLE_RATE_1'), ('ROLE_GUEST');

INSERT INTO users(username, password)
VALUES ('admin', '$2y$10$HN8C15Z/au5GHbeveDB44uj5JTg7APqit9xgEv53TTpnRQrYAKRza'),
       ('user', '$2y$10$/1FO8OFVKPZshG8a0fMINOkqXqKWv1Z1U8y5dmkBKzSYVeSEcKyva'),
       ('guest', '$2y$10$I5lvkcD2e65Fyq.8ZBt8eOlaZ8JYuGyLb6b3ESOnoKyP6.d5gTI3q');

INSERT INTO user_roles (user_id, role_id)
SELECT u.id AS user_id, r.id AS role_id
FROM users u
         JOIN roles r ON (u.username = 'admin' AND r.name = 'ROLE_ADMIN')
UNION ALL
SELECT u.id AS user_id, r.id AS role_id
FROM users u
         JOIN roles r ON (u.username = 'user' AND r.name IN ('ROLE_USER', 'ROLE_RATE_1'))
UNION ALL
SELECT u.id AS user_id, r.id AS role_id
FROM users u
         JOIN roles r ON (u.username = 'guest' AND r.name = 'ROLE_GUEST');