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
class AmountBasedDiscountTest {

    @Mock
    private DiscountProperties discountProperties;

    @InjectMocks
    private AmountBasedDiscount amountBasedDiscount;

    @BeforeEach
    void setUp() {
        when(discountProperties.getAmountThresholds()).thenReturn(List.of(10, 100));
        when(discountProperties.getAmountValues()).thenReturn(List.of(BigDecimal.valueOf(2.0), BigDecimal.valueOf(5.0)));
    }

    @Test
    void applyDiscount_belowThreshold() {
        BigDecimal totalPrice = BigDecimal.valueOf(50);
        BigDecimal result = amountBasedDiscount.applyDiscount(totalPrice, 5);
        assertEquals(totalPrice, result);
    }

    @Test
    void applyDiscount_aboveFirstThreshold() {
        BigDecimal totalPrice = BigDecimal.valueOf(50);
        BigDecimal result = amountBasedDiscount.applyDiscount(totalPrice, 15);
        assertEquals(BigDecimal.valueOf(48.00), result);
    }

    @Test
    void applyDiscount_aboveSecondThreshold() {
        BigDecimal totalPrice = BigDecimal.valueOf(150);
        BigDecimal result = amountBasedDiscount.applyDiscount(totalPrice, 120);
        assertEquals(BigDecimal.valueOf(143.00), result);
    }
}
