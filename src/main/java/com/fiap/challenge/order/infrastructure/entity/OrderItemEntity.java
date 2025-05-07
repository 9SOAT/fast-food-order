package com.fiap.challenge.order.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private Long productId;

    @NotNull
    private String productName;

    @NotNull
    @Enumerated(STRING)
    private ProductCategoryEntity productCategory;

    @Min(1)
    @NotNull
    private Integer quantity;

    @Min(0)
    @NotNull
    @Column(scale = 2)
    private BigDecimal price;

    @Min(0)
    @NotNull
    @Column(scale = 2)
    private BigDecimal subtotal;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
