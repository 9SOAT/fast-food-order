package com.fiap.challenge.order.domain.model.payment;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Payment {
    private Long id;
    private String qrCode;
    private PaymentType type;
    @NonNull
    private BigDecimal amount;
    private String transactionId;
    private PaymentStatus status;
    private LocalDateTime approvedAt;

    public void approve() {
        this.status = PaymentStatus.APPROVED;
        this.approvedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = PaymentStatus.REJECTED;
    }

    public boolean isPendingStatus() {
        return this.status.isPending();
    }
}
