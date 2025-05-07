package com.fiap.challenge.order.infrastructure.mapper;

import com.fiap.challenge.order.domain.model.order.Order;
import com.fiap.challenge.order.domain.model.order.OrderItem;
import com.fiap.challenge.order.domain.model.order.OrderStatus;
import com.fiap.challenge.order.infrastructure.entity.OrderEntity;
import com.fiap.challenge.order.infrastructure.entity.OrderItemEntity;
import com.fiap.challenge.order.infrastructure.entity.OrderStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    OrderItemEntity toOrderItemEntity(OrderItem orderItem);

    OrderItem toOrderItem(OrderItemEntity orderItemEntity);

    OrderEntity toOrderEntity(Order order);

    Order toOrder(OrderEntity orderEntity);

    OrderStatusEntity toOrderStatusEntity(OrderStatus orderStatus);
}
