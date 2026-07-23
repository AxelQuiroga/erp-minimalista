-- Clientes
INSERT INTO customer (id, name, email, phone, address, active, created_at)
VALUES (1, 'Juan Pérez', 'juan@example.com', '123456789', 'Calle 1', true, '2026-01-15 10:00:00');

INSERT INTO customer (id, name, email, phone, address, active, created_at)
VALUES (2, 'María García', 'maria@example.com', '987654321', 'Calle 2', false, '2026-01-20 10:00:00');

-- Productos
INSERT INTO product (id, category_id, name, sku, cost_price, sale_price, current_stock, active)
VALUES (1, 1, 'Mouse', 'MOU-001', 50.00, 80.00, 10, true);

INSERT INTO product (id, category_id, name, sku, cost_price, sale_price, current_stock, active)
VALUES (2, 1, 'Teclado', 'TEC-001', 100.00, 150.00, 5, true);

INSERT INTO product (id, category_id, name, sku, cost_price, sale_price, current_stock, active)
VALUES (3, 1, 'Monitor Inactivo', 'MON-001', 200.00, 350.00, 3, false);

-- Ventas
INSERT INTO sale (id, customer_id, created_at, status, payment_method, notes, total_amount, cancellation_reason)
VALUES (1, 1, '2026-06-01 10:00:00', 'COMPLETED', 'EFECTIVO', 'Venta normal', 310.00, NULL);

INSERT INTO sale (id, customer_id, created_at, status, payment_method, notes, total_amount, cancellation_reason)
VALUES (2, 1, '2026-06-15 14:30:00', 'CANCELLED', 'TARJETA', 'Cancelada por cliente', 80.00, 'Cliente devolvió');

INSERT INTO sale (id, customer_id, created_at, status, payment_method, notes, total_amount, cancellation_reason)
VALUES (3, 2, '2026-07-01 09:00:00', 'COMPLETED', 'TRANSFERENCIA', 'Cliente inactivo al momento', 150.00, NULL);

-- Detalles de ventas
INSERT INTO sale_detail (id, sale_id, product_id, quantity, unit_price)
VALUES (1, 1, 1, 2, 80.00);   -- 2 Mouse x $80 = $160

INSERT INTO sale_detail (id, sale_id, product_id, quantity, unit_price)
VALUES (2, 1, 2, 1, 150.00);  -- 1 Teclado x $150 = $150 (total $310)

INSERT INTO sale_detail (id, sale_id, product_id, quantity, unit_price)
VALUES (3, 2, 1, 1, 80.00);   -- 1 Mouse x $80 = $80

INSERT INTO sale_detail (id, sale_id, product_id, quantity, unit_price)
VALUES (4, 3, 2, 1, 150.00);  -- 1 Teclado x $150 = $150

-- Resetear secuencias para IDs auto-generados
ALTER TABLE customer ALTER COLUMN id RESTART WITH 100;
ALTER TABLE product ALTER COLUMN id RESTART WITH 100;
ALTER TABLE sale ALTER COLUMN id RESTART WITH 100;
ALTER TABLE sale_detail ALTER COLUMN id RESTART WITH 100;
