-- V9__create_reviews.sql

-- Таблица отзывов (ссылки на users, products)
CREATE TABLE reviews (
                         id SERIAL PRIMARY KEY,
                         user_id INTEGER REFERENCES users (id),
                         product_id INTEGER REFERENCES products (id),
                         rating INTEGER NOT NULL,
                         comment TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
