package com.fiap.challenge.order.domain.ports.inbound;

import com.fiap.challenge.order.domain.model.PageResult;
import com.fiap.challenge.order.domain.model.order.Order;
import com.fiap.challenge.order.domain.model.order.OrderStatus;

import java.util.List;

public interface OrderService {

    PageResult<Order> getAllByStatus(List<OrderStatus> status, int page, int size);

    Order saveOrder(Order order);

    void updateStatus(Long orderId, OrderStatus status);

    void approveOrderPayment(Long orderId);

    void rejectOrderPayment(Long orderId);

    PageResult<Order> getAllByStatusInOrderByCreatedAt(List<OrderStatus> status, int page, int size);
}
