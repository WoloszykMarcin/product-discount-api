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

    private BigDecimal totalPrice = BigDecimal.ZERO;

    public void addItem(CartItem item) {
        item.setCart(this);
        this.items.add(item);
        updateTotalPrice();
    }

    public void removeItem(CartItem item) {
        this.items.remove(item);
        item.setCart(null);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        this.totalPrice = items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}