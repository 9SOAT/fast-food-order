package com.fiap.challenge.order.application.response;

import com.fiap.challenge.order.domain.model.product.ProductStatus;

import java.math.BigDecimal;
import java.util.Set;

public record ProductView(
    Long id,
    String name,
    String description,
    Set<String> images,
    BigDecimal price,
    ProductCategoryView category,
    ProductStatus status
) {
}
