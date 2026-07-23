INSERT INTO customer (id, name, email, phone, address, active, created_at)
VALUES (1, 'Juan Pérez', 'juan@example.com', '123456789', 'Calle 1', true, '2026-01-15 10:00:00');

INSERT INTO customer (id, name, email, phone, address, active, created_at)
VALUES (2, 'María García', 'maria@example.com', '987654321', 'Calle 2', false, '2026-01-20 10:00:00');

INSERT INTO customer (id, name, email, phone, address, active, created_at)
VALUES (3, 'Pedro López', 'pedro@example.com', '555555555', 'Calle 3', true, '2026-02-01 10:00:00');

INSERT INTO customer (id, name, email, phone, address, active, created_at)
VALUES (4, 'Ana Martínez', 'ana@example.com', '111111111', 'Calle 4', true, '2026-03-01 10:00:00');

ALTER TABLE customer ALTER COLUMN id RESTART WITH 100;
