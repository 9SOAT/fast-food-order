package com.fiap.challenge.order.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
@Table(name = "payment",
    indexes = {
        @Index(name = "idx_payment_transaction_id", columnList = "transactionId")
    }
)
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(STRING)
    private PaymentTypeEntity type;

    @NotNull
    @Enumerated(STRING)
    private PaymentStatusEntity status;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    private String transactionId;

    @NotNull
    private String qrCode;

    @OneToOne(mappedBy = "payment")
    private OrderEntity order;

    private LocalDateTime approvedAt;
}
