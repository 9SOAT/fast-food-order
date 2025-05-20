package com.fiap.challenge.order.application.controller;

import com.fiap.challenge.order.application.request.OrderStatusFilter;
import com.fiap.challenge.order.application.request.OrderStatusMutation;
import com.fiap.challenge.order.application.response.OrderView;
import com.fiap.challenge.order.domain.model.PageResult;
import com.fiap.challenge.order.domain.model.order.Order;
import com.fiap.challenge.order.domain.ports.inbound.OrderService;
import com.fiap.challenge.order.infrastructure.mapper.PageResultMapper;
import com.fiap.challenge.order.infrastructure.mapper.ViewMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fiap.challenge.order.domain.model.order.OrderStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ViewMapper viewMapper;

    public OrderController(OrderService orderService, ViewMapper viewMapper) {
        this.orderService = orderService;
        this.viewMapper = viewMapper;
    }

    @ResponseStatus(CREATED)
    @PostMapping("/create")
    public void saveOrder(@RequestBody Order order) {
        orderService.saveOrder(order);
    }

    @GetMapping()
    public PageResult<OrderView> getOrdersByStatus(
        @RequestParam @Min(1) int page,
        @Max(20) @RequestParam int size,
        @RequestParam(name = "status") OrderStatusFilter statusFilter
    ) {
        PageResult<Order> orderPage = orderService.getAllByStatus(statusFilter.getOrderStatuses(), page, size);
        return PageResultMapper.transformContent(orderPage, viewMapper::toOrderView);
    }

    @GetMapping("/list")
    public PageResult<OrderView> getOrders(
        @RequestParam @Min(1) int page,
        @Max(20) @RequestParam int size
    ) {
        PageResult<Order> orderPage = orderService.getAllByStatusInOrderByCreatedAt(List.of(IN_PREPARATION, READY_FOR_PICKUP, READY_FOR_PREPARATION), page, size);
        return PageResultMapper.transformContent(orderPage, viewMapper::toOrderView);
    }

    @Transactional
    @PatchMapping("/payment/{id}/approve")
    public void approveOrderPayment(
            @PathVariable Long id
    ) {
        orderService.approveOrderPayment(id);
    }

    @Transactional
    @PatchMapping("/payment/{id}/reject")
    public void rejectOrderPayment(
            @PathVariable Long id
    ) {
        orderService.rejectOrderPayment(id);
    }

    @Transactional
    @PatchMapping("/{id}/status")
    public void updateOrderStatus(
        @PathVariable Long id,
        @Valid @RequestBody OrderStatusMutation orderStatusMutation
    ) {
        orderService.updateStatus(id, orderStatusMutation.status());
    }
}
