package com.apis.productdiscountapi.service.strategies;

import com.apis.productdiscountapi.config.DiscountProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PercentageBasedDiscountTest {

    @Mock
    private DiscountProperties discountProperties;

    @InjectMocks
    private PercentageBasedDiscount percentageBasedDiscount;

    @BeforeEach
    void setUp() {
        when(discountProperties.getPercentageThresholds()).thenReturn(List.of(10, 50));
        when(discountProperties.getPercentageValues()).thenReturn(List.of(BigDecimal.valueOf(3.0), BigDecimal.valueOf(5.0)));
    }

    @Test
    void applyDiscount_belowThreshold() {
        BigDecimal totalPrice = BigDecimal.valueOf(50);
        BigDecimal result = percentageBasedDiscount.applyDiscount(totalPrice, 5);
        assertEquals(totalPrice.setScale(2), result.setScale(2));
    }

    @Test
    void applyDiscount_aboveFirstThreshold() {
        BigDecimal totalPrice = BigDecimal.valueOf(100);
        BigDecimal result = percentageBasedDiscount.applyDiscount(totalPrice, 15);
        assertEquals(BigDecimal.valueOf(97.00).setScale(2), result.setScale(2));
    }

    @Test
    void applyDiscount_aboveSecondThreshold() {
        BigDecimal totalPrice = BigDecimal.valueOf(100);
        BigDecimal result = percentageBasedDiscount.applyDiscount(totalPrice, 60);
        assertEquals(BigDecimal.valueOf(92.15).setScale(2), result.setScale(2));
    }
}
