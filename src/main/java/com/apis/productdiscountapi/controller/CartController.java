package com.apis.productdiscountapi.controller;

import com.apis.productdiscountapi.dto.CartDTO;
import com.apis.productdiscountapi.exception.CartNotFoundException;
import com.apis.productdiscountapi.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public CartDTO createCart() {
        return cartService.createCart();
    }

    @GetMapping("/{id}")
    public CartDTO getCart(@PathVariable UUID id) {
        return cartService.getCartWithItems(id)
                .orElseThrow(() -> new CartNotFoundException(id));
    }

    @PostMapping("/{cartId}/products")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable UUID cartId, @RequestParam UUID productId, @RequestParam int quantity) {
        try {
            CartDTO updatedCart = cartService.addProductToCart(cartId, productId, quantity);
            return ResponseEntity.ok(updatedCart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cartId}/products")
    public ResponseEntity<CartDTO> removeProductFromCart(@PathVariable UUID cartId, @RequestParam UUID productId) {
        try {
            CartDTO updatedCart = cartService.removeProductFromCart(cartId, productId);
            return ResponseEntity.ok(updatedCart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{cartId}/totalPrice")
    public ResponseEntity<BigDecimal> getTotalPrice(@PathVariable UUID cartId, @RequestParam String discountType) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId, discountType);
            return ResponseEntity.ok(totalPrice);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
