-- liquibase formatted sql

-- changeset fabiosiqueira:1747353324000-1
ALTER TABLE order_item
    DROP COLUMN product_category;

-- changeset fabiosiqueira:1747353324000-2
ALTER TABLE order_item
    ADD product_category VARCHAR(255) NOT NULL;

-- changeset fabiosiqueira:1747353324000-3
ALTER TABLE order_item
    ALTER COLUMN product_category SET NOT NULL;

