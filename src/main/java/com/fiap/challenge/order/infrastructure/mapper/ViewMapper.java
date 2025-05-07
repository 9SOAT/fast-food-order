package com.fiap.challenge.order.infrastructure.mapper;

import com.fiap.challenge.order.application.response.*;
import com.fiap.challenge.order.domain.model.order.Order;
import com.fiap.challenge.order.domain.model.payment.Payment;
import com.fiap.challenge.order.domain.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ViewMapper {

    ViewMapper INSTANCE = Mappers.getMapper(ViewMapper.class);

    public ProductView toProductView(Product product);

    public PaymentView toPaymentView(Payment payment);

    public OrderView toOrderView(Order order);
}
