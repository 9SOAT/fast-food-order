package com.fiap.challenge.order.infrastructure.entity;

public enum OrderStatusEntity {
    CANCELLED,
    WAITING_PAYMENT,
    READY_FOR_PREPARATION,
    IN_PREPARATION,
    READY_FOR_PICKUP,
    FINISHED;
}
