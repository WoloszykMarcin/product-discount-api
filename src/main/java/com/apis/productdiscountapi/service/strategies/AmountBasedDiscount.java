package com.apis.productdiscountapi.service.strategies;

import com.apis.productdiscountapi.config.DiscountProperties;
import com.apis.productdiscountapi.service.DiscountStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AmountBasedDiscount implements DiscountStrategy {

    private DiscountProperties discountProperties;

    public AmountBasedDiscount(DiscountProperties discountProperties) {
        this.discountProperties = discountProperties;
    }

    @Override
    public String getType() {
        return "AMOUNT";
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal totalPrice, int quantity) {
        List<Integer> thresholds = discountProperties.getAmountThresholds();
        List<BigDecimal> values = discountProperties.getAmountValues();

        if (thresholds == null || values == null) {
            throw new IllegalStateException("Discount properties not configured properly.");
        }

        for (int i = 0; i < thresholds.size(); i++) {
            if (quantity >= thresholds.get(i)) {
                totalPrice = totalPrice.subtract(values.get(i));
            }
        }
        return totalPrice;
    }
}
