package com.fiap.challenge.order.domain.model.order;

import com.fiap.challenge.order.domain.model.product.ProductCategory;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Long id;
    @NonNull
    private Long productId;
    @NonNull
    private String productName;
    @NonNull
    private ProductCategory productCategory;
    @NonNull
    private Integer quantity;
    @NonNull
    private BigDecimal price;
    @NonNull
    private BigDecimal subtotal;
}
