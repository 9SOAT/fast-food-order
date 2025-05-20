package com.fiap.challenge.order.domain.ports.outbound;

import com.fiap.challenge.order.domain.model.PageResult;
import com.fiap.challenge.order.domain.model.order.Order;
import com.fiap.challenge.order.domain.model.order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    public Order save(Order order);

    PageResult<Order> findAllByStatusIn(List<OrderStatus> inProgressStatuses, int page, int size);

    Optional<Order> findByPaymentId(Long paymentId);

    Optional<Order> findById(Long id);

    PageResult<Order> findAllByStatusInOrderByCreatedAt(List<OrderStatus> inProgressStatuses, int page, int size);
}
