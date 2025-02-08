-- V6__create_carts_and_cart_items.sql

-- Таблица корзин (ссылка на users)
CREATE TABLE carts (
                       id SERIAL PRIMARY KEY,
                       user_id INTEGER REFERENCES users (id) UNIQUE,
                       total_price DECIMAL(15, 2) DEFAULT 0
);

-- Таблица товаров в корзинах (ссылки на carts и products)
CREATE TABLE cart_items (
                            id SERIAL PRIMARY KEY,
                            cart_id INTEGER REFERENCES carts (id),
                            product_id INTEGER REFERENCES products (id),
                            quantity INTEGER NOT NULL,
                            price DECIMAL(15, 2) NOT NULL
);
