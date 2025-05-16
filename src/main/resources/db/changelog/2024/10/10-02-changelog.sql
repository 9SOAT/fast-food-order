-- liquibase formatted sql

-- changeset fabiosiqueira:1747353323000-1
ALTER TABLE order_item
    ADD product_category SMALLINT;
ALTER TABLE order_item
    ADD product_name VARCHAR(255);

-- changeset fabiosiqueira:1747353323000-2
ALTER TABLE order_item
    ALTER COLUMN product_category SET NOT NULL;

-- changeset fabiosiqueira:1747353323000-3
ALTER TABLE order_item
    ALTER COLUMN product_name SET NOT NULL;

