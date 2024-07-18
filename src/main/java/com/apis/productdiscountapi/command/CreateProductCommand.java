package com.apis.productdiscountapi.command;

import com.apis.productdiscountapi.validation.annotation.UniqueProductName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductCommand {
    @UniqueProductName
    private String name;
    private BigDecimal price;
}