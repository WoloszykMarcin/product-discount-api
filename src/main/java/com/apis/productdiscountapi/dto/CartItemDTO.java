package com.apis.productdiscountapi.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CartItemDTO {
    private UUID id;
    private ProductDTO product;
    private int quantity;
    private BigDecimal totalPrice;
}
