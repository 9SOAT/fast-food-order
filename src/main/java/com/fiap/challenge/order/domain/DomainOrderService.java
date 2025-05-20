package com.fiap.challenge.order.domain;

import com.fiap.challenge.order.domain.model.PageResult;
import com.fiap.challenge.order.domain.model.exception.NotFoundException;

import com.fiap.challenge.order.domain.model.order.Order;
import com.fiap.challenge.order.domain.model.order.OrderStatus;
import com.fiap.challenge.order.domain.ports.inbound.OrderService;
import com.fiap.challenge.order.domain.ports.outbound.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DomainOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final String ORDER_NOT_FOUND = "Order not found. ID: %s";

    public DomainOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public PageResult<Order> getAllByStatus(List<OrderStatus> status, int page, int size) {
        return orderRepository.findAllByStatusIn(status, page, size);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public PageResult<Order> getAllByStatusInOrderByCreatedAt(List<OrderStatus> status, int page, int size) {
        return orderRepository.findAllByStatusInOrderByCreatedAt(status, page, size);
    }

    @Override
    public void updateStatus(Long orderId, OrderStatus status) {
        String notFoundMessage = String.format(ORDER_NOT_FOUND, orderId);
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException(notFoundMessage, "ORDER_NOT_FOUND"));

        order.setStatus(status);
        orderRepository.save(order);
    }
}
