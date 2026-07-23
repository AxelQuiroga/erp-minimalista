INSERT INTO product (id, category_id, name, sku, cost_price, sale_price, current_stock, active)
VALUES (1, 1, 'Mouse', 'MOU-001', 50.00, 80.00, 10, true);

INSERT INTO product (id, category_id, name, sku, cost_price, sale_price, current_stock, active)
VALUES (2, 1, 'Teclado', 'TEC-001', 100.00, 150.00, 5, true);

INSERT INTO product (id, category_id, name, sku, cost_price, sale_price, current_stock, active)
VALUES (3, 1, 'Monitor Inactivo', 'MON-001', 200.00, 350.00, 3, false);

INSERT INTO product (id, category_id, name, sku, cost_price, sale_price, current_stock, active)
VALUES (4, 1, 'Mouse Inalámbrico', 'MOU-002', 75.00, 120.00, 0, true);

INSERT INTO product (id, category_id, name, sku, cost_price, sale_price, current_stock, active)
VALUES (5, 1, 'Teclado Mecánico', 'TEC-002', 150.00, 250.00, 8, true);

ALTER TABLE product ALTER COLUMN id RESTART WITH 100;
