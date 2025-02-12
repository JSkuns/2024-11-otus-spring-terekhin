CREATE TABLE IF NOT EXISTS books
(
    id bigserial PRIMARY KEY,
    title VARCHAR(255),
    author_id BIGINT REFERENCES authors (id) ON DELETE CASCADE,
    genre_id BIGINT REFERENCES genres (id) ON DELETE CASCADE
);