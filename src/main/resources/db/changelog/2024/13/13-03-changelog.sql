-- liquibase formatted sql

-- changeset victorambiel:1747353329000-1
ALTER TABLE orders ALTER COLUMN consumer_id DROP NOT NULL;