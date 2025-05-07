package com.fiap.challenge.order.infrastructure.mapper;

import com.fiap.challenge.order.domain.model.order.Order;
import com.fiap.challenge.order.domain.model.order.OrderItem;
import com.fiap.challenge.order.domain.model.order.OrderStatus;
import com.fiap.challenge.order.domain.model.payment.Payment;
import com.fiap.challenge.order.domain.model.product.ProductCategory;
import com.fiap.challenge.order.domain.model.product.ProductStatus;
import com.fiap.challenge.order.infrastructure.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    public ProductCategoryEntity toCategoryEntity(ProductCategory category);

    public PaymentEntity toPaymentEntity(Payment payment);

    public Payment toPayment(PaymentEntity paymentEntity);

    public OrderItemEntity toOrderItemEntity(OrderItem orderItem);

    public OrderItem toOrderItem(OrderItemEntity orderItemEntity);

    public OrderEntity toOrderEntity(Order order);

    public Order toOrder(OrderEntity orderEntity);

    OrderStatusEntity toOrderStatusEntity(OrderStatus orderStatus);

    ProductStatusEntity toStatusEntity(ProductStatus productStatus);
}
