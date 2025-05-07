package com.fiap.challenge.order.application.request;

import com.fiap.challenge.order.domain.model.order.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusMutation(
    @NotNull OrderStatus status
) {
}

