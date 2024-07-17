package com.apis.productdiscountapi.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProductDTO {
    private UUID id;
    private String name;
    private BigDecimal price;
}
