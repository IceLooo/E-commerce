-- Таблица платежей
CREATE TABLE payments (
                          id SERIAL PRIMARY KEY,
                          order_id INTEGER REFERENCES orders (id),
                          payment_method VARCHAR(100) NOT NULL,
                          status VARCHAR(50) NOT NULL,
                          transaction_id VARCHAR(255)
);