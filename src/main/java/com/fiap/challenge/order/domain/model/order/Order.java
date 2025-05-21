package com.fiap.challenge.order.domain.model.order;

import com.fiap.challenge.order.domain.model.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Long cartId;
    private String consumerId;
    private Payment payment;
    private List<OrderItem> items;
    private OrderStatus status;
    private BigDecimal total;
    private Instant createdAt;
}
