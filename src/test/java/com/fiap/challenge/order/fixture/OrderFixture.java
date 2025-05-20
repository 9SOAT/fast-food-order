package com.fiap.challenge.order.fixture;

import com.fiap.challenge.order.domain.model.PageResult;
import com.fiap.challenge.order.domain.model.order.Order;
import com.fiap.challenge.order.domain.model.order.OrderItem;
import com.fiap.challenge.order.domain.model.order.OrderStatus;
import com.fiap.challenge.order.domain.model.payment.Payment;
import com.fiap.challenge.order.domain.model.product.ProductCategory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class OrderFixture {

    public static Order validOrder() {
        return new Order(
            1L,
            1L,
            "12345678901",
            new Payment(
                null
            ),
            List.of(new OrderItem(1L, "1", "X-Tudo", ProductCategory.SANDWICH, 2, new BigDecimal("20.00"), new BigDecimal("40.00"))), OrderStatus.WAITING_PAYMENT, new BigDecimal("40.00"),
            Instant.now()
        );
    }

    public static PageResult<Order> pageResultWithOneOrder() {
        return new PageResult<>(List.of(validOrder()), 1,1,1,1);
    }
}
