package com.apis.productdiscountapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Product {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private BigDecimal price;
}
