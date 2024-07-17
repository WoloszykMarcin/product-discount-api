package com.apis.productdiscountapi.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiscountService {

    private final Map<String, DiscountStrategy> discountStrategies = new HashMap<>();

    private List<DiscountStrategy> strategies;

    public DiscountService(List<DiscountStrategy> strategies) {
        this.strategies = strategies;
    }

    @PostConstruct
    public void init() {
        for (DiscountStrategy strategy : strategies) {
            discountStrategies.put(strategy.getType().toUpperCase(), strategy);
        }
    }

    public BigDecimal applyDiscount(BigDecimal totalPrice, int quantity, String discountType) {
        DiscountStrategy strategy = discountStrategies.get(discountType.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid discount type");
        }
        return strategy.applyDiscount(totalPrice, quantity);
    }
}
