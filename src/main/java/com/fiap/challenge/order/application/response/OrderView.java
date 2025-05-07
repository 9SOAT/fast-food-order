package com.fiap.challenge.order.application.response;

import java.math.BigDecimal;
import java.util.List;

public record OrderView(
    Long id,
    Long cartId,
    Long consumerId,
    PaymentView payment,
    List<OrderItemView> items,
    OrderStatusView status,
    BigDecimal total,
    Long waitingMinutes
) {
}
