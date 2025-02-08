-- V5__create_addresses.sql

-- Таблица адресов (ссылка на users)
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
