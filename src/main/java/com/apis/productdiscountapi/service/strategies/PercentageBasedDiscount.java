package com.apis.productdiscountapi.service.strategies;

import com.apis.productdiscountapi.config.DiscountProperties;
import com.apis.productdiscountapi.service.DiscountStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PercentageBasedDiscount implements DiscountStrategy {

    private final DiscountProperties discountProperties;

    public PercentageBasedDiscount(DiscountProperties discountProperties) {
        this.discountProperties = discountProperties;
    }

    @Override
    public String getType() {
        return "PERCENTAGE";
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal totalPrice, int quantity) {
        List<Integer> thresholds = discountProperties.getPercentageThresholds();
        List<BigDecimal> values = discountProperties.getPercentageValues();

        if (thresholds == null || values == null) {
            throw new IllegalStateException("Discount properties not configured properly.");
        }

        for (int i = 0; i < thresholds.size(); i++) {
            if (quantity >= thresholds.get(i)) {
                BigDecimal discount = values.get(i).divide(BigDecimal.valueOf(100));
                totalPrice = totalPrice.multiply(BigDecimal.ONE.subtract(discount));
            }
        }
        return totalPrice;
    }
}
