package com.fiap.challenge.order.infrastructure.config;

import com.fiap.challenge.order.domain.DomainOrderService;
import com.fiap.challenge.order.domain.ports.inbound.OrderService;
import com.fiap.challenge.order.domain.ports.outbound.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public OrderService orderService(OrderRepository orderRepository) {
        return new DomainOrderService(orderRepository);
    }
}
