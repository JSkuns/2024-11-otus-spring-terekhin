CREATE TABLE IF NOT EXISTS equipments
(
    id              BIGSERIAL PRIMARY KEY,
    equipment_type  VARCHAR(255) NOT NULL,  -- Тип оборудования (например, токарный станок, фрезерный станок)
    model           VARCHAR(255) NOT NULL   -- Модель оборудования
);

INSERT INTO equipments (
    equipment_type,
    model
) VALUES
      ('Lathe', 'HAAS TL-2'),                               -- Токарный станок HAAS TL-2
      ('Mill', 'DMG MORI DMU 65 monoBLOCK'),                -- Фрезерный станок DMG MORI DMU 65 monoBLOCK
      ('Drill Press', 'DEWALT DW255K'),                     -- Сверлильный станок DEWALT DW255K
      ('CNC Lathe', 'MAZAK INTEGREX e-Series'),             -- CNC-токарный станок MAZAK INTEGREX e-Series
      ('Laser Cutting Machine', 'TRUMPF TruLaser 3030');    -- Лазерный раскрой TRUMPF TruLaser 3030