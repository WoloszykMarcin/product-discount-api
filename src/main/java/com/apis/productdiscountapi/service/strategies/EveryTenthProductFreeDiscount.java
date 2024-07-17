package com.apis.productdiscountapi.service.strategies;

import com.apis.productdiscountapi.service.DiscountStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class EveryTenthProductFreeDiscount implements DiscountStrategy {

    @Override
    public String getType() {
        return "EVERY_TENTH_FREE";
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal totalPrice, int quantity) {
        int freeProducts = quantity / 10;
        BigDecimal averagePrice = totalPrice.divide(BigDecimal.valueOf(quantity), RoundingMode.HALF_UP);
        BigDecimal discount = averagePrice.multiply(BigDecimal.valueOf(freeProducts));
        return totalPrice.subtract(discount);
    }
}
