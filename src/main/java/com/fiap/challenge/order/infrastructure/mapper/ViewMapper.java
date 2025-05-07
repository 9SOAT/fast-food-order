package com.fiap.challenge.order.infrastructure.mapper;

import com.fiap.challenge.order.application.response.OrderView;
import com.fiap.challenge.order.application.response.PaymentView;
import com.fiap.challenge.order.domain.model.order.Order;
import com.fiap.challenge.order.domain.model.payment.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ViewMapper {

    ViewMapper INSTANCE = Mappers.getMapper(ViewMapper.class);

    public PaymentView toPaymentView(Payment payment);

    public OrderView toOrderView(Order order);
}
