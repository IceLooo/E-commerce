-- Таблица категорий
CREATE TABLE categories (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            description TEXT
);

-- Таблица брендов
CREATE TABLE brands (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        description TEXT
);

-- Таблица продуктов
CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          price DECIMAL(15, 2) NOT NULL,
                          weight DOUBLE PRECISION,
                          category_id INTEGER REFERENCES categories (id),
                          brand_id INTEGER REFERENCES brands (id),
                          stock INTEGER NOT NULL,
                          image_url VARCHAR(500)
);

-- Таблица пользователей
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone_number VARCHAR(20),
                       enabled BOOLEAN NOT NULL DEFAULT FALSE,
                       role_id INTEGER REFERENCES roles (id)
);

-- Таблица адресов---
CREATE TABLE addresses (
                           id SERIAL PRIMARY KEY,
                           country VARCHAR(255) NOT NULL,
                           city VARCHAR(255) NOT NULL,
                           region VARCHAR(255),
                           street VARCHAR(255) NOT NULL,
                           postal_code VARCHAR(255),
                           entrance VARCHAR(255),
                           apartment_office VARCHAR(255),
                           courier_comments VARCHAR(255),
                           user_id INTEGER REFERENCES users (id)
);

-- Таблица корзин
CREATE TABLE carts (
                       id SERIAL PRIMARY KEY,
                       user_id INTEGER REFERENCES users (id) UNIQUE,
                       total_price DECIMAL(15, 2) DEFAULT 0
);

-- Таблица товаров в корзинах
CREATE TABLE cart_items (
                            id SERIAL PRIMARY KEY,
                            cart_id INTEGER REFERENCES carts (id),
                            product_id INTEGER REFERENCES products (id),
                            quantity INTEGER NOT NULL,
                            price DECIMAL(15, 2) NOT NULL
);


-- Таблица заказов
CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        user_id INTEGER REFERENCES users (id),
                        total_price DECIMAL(15, 2) NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        address_id INTEGER REFERENCES addresses (id) UNIQUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица товаров в заказах
CREATE TABLE order_items (
                             id SERIAL PRIMARY KEY,
                             order_id INTEGER REFERENCES orders (id),
                             product_id INTEGER REFERENCES products (id),
                             quantity INTEGER NOT NULL,
                             price DECIMAL(15, 2) NOT NULL
);

-- Таблица отзывов
CREATE TABLE reviews (
                         id SERIAL PRIMARY KEY,
                         user_id INTEGER REFERENCES users (id),
                         product_id INTEGER REFERENCES products (id),
                         rating INTEGER NOT NULL,
                         comment TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица характеристик продуктов
CREATE TABLE features (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          value VARCHAR(255) NOT NULL,
                          product_id INTEGER REFERENCES products (id)
);

-- Таблица платежей
CREATE TABLE payments (
                          id SERIAL PRIMARY KEY,
                          order_id INTEGER REFERENCES orders (id),
                          payment_method VARCHAR(100) NOT NULL,
                          status VARCHAR(50) NOT NULL,
                          transaction_id VARCHAR(255)
);

-- Таблица ролей
CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(50) UNIQUE NOT NULL
);

