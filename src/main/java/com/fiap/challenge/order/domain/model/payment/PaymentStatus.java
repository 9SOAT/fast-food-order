package com.fiap.challenge.order.domain.model.payment;

public enum PaymentStatus {
    PENDING,
    APPROVED,
    REJECTED;

    public boolean isPending() {
        return this == PENDING;
    }
}
