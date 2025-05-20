package com.fiap.challenge.order.infrastructure.entity;

import com.fiap.challenge.order.domain.model.payment.Payment;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderEntityTest {

    @Test
    void orderEntityRemoveItem() {
        OrderEntity order = new OrderEntity();
        OrderItemEntity item = new OrderItemEntity();
        order.setItems(List.of(item));
        assertNotNull(item.getOrder());
        assertNotNull(order.getItems());
        assertFalse(order.getItems().isEmpty());
    }

    @Test
    void setPayment() {
        OrderEntity order = new OrderEntity();
        Payment payment = new Payment(1L);
        order.setPayment(payment);

        assertNotNull(order.getPaymentId());
        assertNotNull(payment.getId());
    }
}
