package com.fiap.challenge.order.fixture;

import com.fiap.challenge.order.application.response.OrderStatusView;
import com.fiap.challenge.order.application.response.OrderView;

import java.math.BigDecimal;
import java.util.List;

public class OrderViewFixture {

    public static OrderView validOrderView() {
        return new OrderView(
            1L,
            1L,
            "12345678901",
            PaymentViewFixture.validPaymentView(),
            List.of(OrderItemViewFixture.validItem()),
            OrderStatusView.READY_FOR_PREPARATION,
            BigDecimal.valueOf(100),
            5L
        );
    }
}
