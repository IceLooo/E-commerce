-- V3__create_categories_brands_products.sql

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
