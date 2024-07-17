package com.apis.productdiscountapi.service;

import java.math.BigDecimal;

public interface DiscountStrategy {

    String getType();

    BigDecimal applyDiscount(BigDecimal totalPrice, int quantity);
}
