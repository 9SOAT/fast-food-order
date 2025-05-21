package com.fiap.challenge.order.infrastructure.entity;

import com.fiap.challenge.order.domain.model.payment.Payment;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders", indexes = {
    @Index(name = "idx_orders_consumer_id", columnList = "consumer_id"),
    @Index(name = "idx_orders_status", columnList = "status"),
    @Index(name = "uk_orders_cart_id", columnList = "cart_id", unique = true)
})
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String consumerId;

    @NotNull
    private Long cartId;

    @NotNull
    private Long paymentId;

    @NotNull
    @Enumerated(STRING)
    private OrderStatusEntity status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItemEntity> items;

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(scale = 2)
    private BigDecimal total;

    @Column
    @NotNull
    private Instant createdAt;

    public void setItems(List<OrderItemEntity> items) {
        items.forEach(item -> item.setOrder(this));
        this.items = items;
    }

    public void setPayment(Payment payment) {
        this.paymentId = payment.getId();
    }

    public Payment getPayment() {
        return new Payment(this.paymentId);
    }
}
