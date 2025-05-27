CREATE TABLE IF NOT EXISTS threads
(
    id              BIGSERIAL PRIMARY KEY,
    thread_diameter DECIMAL(10, 2) NOT NULL,
    pitch           DECIMAL(10, 2) NOT NULL,
    tolerance_class VARCHAR(255) NOT NULL
    );

INSERT INTO threads (
    thread_diameter,
    pitch,
    tolerance_class
) VALUES
      ('6', '1', '6g'),      -- М6 x 1 мм, класс точности 6g
      ('8', '1.25', '6g'),   -- М8 x 1.25 мм, класс точности 6g
      ('10', '1.5', '6g'),   -- М10 x 1.5 мм, класс точности 6g
      ('12', '1.75', '6g'),  -- М12 x 1.75 мм, класс точности 6g
      ('16', '2', '6g'),     -- М16 x 2 мм, класс точности 6g
      ('20', '2.5', '6g');   -- М20 x 2.5 мм, класс точности 6g