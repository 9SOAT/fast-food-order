package com.fiap.challenge.order.fixture;

import com.fiap.challenge.order.application.response.OrderItemView;
import com.fiap.challenge.order.application.response.ProductCategoryView;

import java.math.BigDecimal;

public class OrderItemViewFixture {
    public static OrderItemView validItem() {
        return new OrderItemView("1", "X-Tudo", ProductCategoryView.SANDWICH, 2, new BigDecimal("20.00"), new BigDecimal("40.00"));
    }

}
