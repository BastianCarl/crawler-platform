-- V1__create_products_table.sql

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
      source VARCHAR(50) NOT NULL,
      url TEXT NOT NULL UNIQUE,
      title TEXT NOT NULL,
      current_price NUMERIC(10,2),
      currency VARCHAR(10),
      current_in_stock BOOLEAN,
      last_hash VARCHAR(64),
      first_seen_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
      last_seen_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);