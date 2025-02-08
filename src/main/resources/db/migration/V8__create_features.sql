-- V8__create_features.sql

-- Таблица характеристик продуктов (ссылка на products)
CREATE TABLE features (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          value VARCHAR(255) NOT NULL,
                          product_id INTEGER REFERENCES products (id)
);
