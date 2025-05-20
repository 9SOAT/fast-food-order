-- liquibase formatted sql

-- changeset victorambiel:1847353329000-1
ALTER TABLE orders DROP CONSTRAINT FK_ORDERS_ON_PAYMENT;
ALTER TABLE payment DROP CONSTRAINT pk_payment;
DROP TABLE payment;