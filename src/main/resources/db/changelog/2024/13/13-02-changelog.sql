-- liquibase formatted sql

-- changeset victorambiel:1747353328000-1
ALTER TABLE order_item ALTER COLUMN product_id TYPE VARCHAR(255) USING product_id::VARCHAR(255);

-- rollback ALTER TABLE order_item ALTER COLUMN product_id TYPE BIGINT USING product_id::BIGINT;