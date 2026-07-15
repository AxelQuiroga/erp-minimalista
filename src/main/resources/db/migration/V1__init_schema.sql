-- Categorías
CREATE TABLE category (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL
);

-- Productos
CREATE TABLE product (
                         id SERIAL PRIMARY KEY,
                         category_id INT REFERENCES category(id),
                         name VARCHAR(255) NOT NULL,
                         sku VARCHAR(50) UNIQUE NOT NULL,
                         cost_price DECIMAL(12, 2) NOT NULL,
                         sale_price DECIMAL(12, 2) NOT NULL,
                         current_stock INT DEFAULT 0
);

-- Movimientos de Stock
CREATE TABLE stock_movement (
                                id SERIAL PRIMARY KEY,
                                product_id INT REFERENCES product(id),
                                quantity INT NOT NULL,
                                movement_type VARCHAR(20) NOT NULL, -- IN, OUT, ADJUSTMENT
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                reason VARCHAR(255)
);

-- Clientes
CREATE TABLE customer (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE
);

-- Ventas
CREATE TABLE sale (
                      id SERIAL PRIMARY KEY,
                      customer_id INT REFERENCES customer(id),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      total_amount DECIMAL(12, 2) NOT NULL
);

-- Detalle de Ventas
CREATE TABLE sale_detail (
                             id SERIAL PRIMARY KEY,
                             sale_id INT REFERENCES sale(id),
                             product_id INT REFERENCES product(id),
                             quantity INT NOT NULL,
                             unit_price DECIMAL(12, 2) NOT NULL -- Precio histórico
);