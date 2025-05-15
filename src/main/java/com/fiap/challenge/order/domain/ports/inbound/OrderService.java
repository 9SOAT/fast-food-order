package com.fiap.challenge.order.domain.ports.inbound;

import com.fiap.challenge.order.domain.model.PageResult;
import com.fiap.challenge.order.domain.model.order.Order;
import com.fiap.challenge.order.domain.model.order.OrderStatus;
import com.fiap.challenge.order.domain.model.payment.PaymentStatus;

import java.util.List;

public interface OrderService {

    PageResult<Order> getAllByStatus(List<OrderStatus> status, int page, int size);

    Order saveOrder(Order order);

    void approvePayment(String transactionId);

    void rejectPayment(String transactionId);

    void approvePayment(Long orderId);

    PaymentStatus getPaymentStatusById(Long id);

    void updateStatus(Long orderId, OrderStatus status);

    PageResult<Order> getAllByStatusInOrderByCreatedAt(List<OrderStatus> status, int page, int size);
}
