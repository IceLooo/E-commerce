-- V7__create_orders_and_order_items.sql

-- Таблица заказов (ссылки на users, addresses)
CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        user_id INTEGER REFERENCES users (id),
                        total_price DECIMAL(15, 2) NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        address_id INTEGER REFERENCES addresses (id) UNIQUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица товаров в заказах (ссылки на orders, products)
CREATE TABLE order_items (
                             id SERIAL PRIMARY KEY,
                             order_id INTEGER REFERENCES orders (id),
                             product_id INTEGER REFERENCES products (id),
                             quantity INTEGER NOT NULL,
                             price DECIMAL(15, 2) NOT NULL
);
