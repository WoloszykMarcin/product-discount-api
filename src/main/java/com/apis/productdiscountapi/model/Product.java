package com.apis.productdiscountapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
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
