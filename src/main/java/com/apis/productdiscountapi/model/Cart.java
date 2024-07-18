package com.apis.productdiscountapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    private UUID id = UUID.randomUUID();

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItem> items = new ArrayList<>();

    @Version
    private int version;

    private BigDecimal totalPrice = BigDecimal.ZERO;

    private int totalQuantity = 0;

    public void addItem(CartItem item) {
        items.add(item);
        recalculateCart();
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        recalculateCart();
    }

    private void recalculateCart() {
        totalPrice = items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalQuantity = items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}