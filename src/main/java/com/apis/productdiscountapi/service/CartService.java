package com.apis.productdiscountapi.service;

import com.apis.productdiscountapi.dto.CartDTO;
import com.apis.productdiscountapi.model.Cart;
import com.apis.productdiscountapi.model.CartItem;
import com.apis.productdiscountapi.model.Product;
import com.apis.productdiscountapi.repository.CartRepository;
import com.apis.productdiscountapi.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    private CartRepository cartRepository;

    private ProductRepository productRepository;

    private DiscountService discountService;

    private ModelMapper modelMapper;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, DiscountService discountService, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.discountService = discountService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public CartDTO createCart() {
        Cart cart = cartRepository.save(new Cart());
        return modelMapper.map(cart, CartDTO.class);
    }

    @Transactional(readOnly = true)
    public Optional<CartDTO> getCartWithItems(UUID id) {
        return cartRepository.findByIdWithItems(id).map(cart -> modelMapper.map(cart, CartDTO.class));
    }

    @Retryable(
            value = {DataIntegrityViolationException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    @Transactional
    public CartDTO addProductToCart(UUID cartId, UUID productId, int quantity) {
        Cart cart = cartRepository.findByIdWithItems(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.addItem(item);
        }

        cartRepository.save(cart);
        return modelMapper.map(cart, CartDTO.class);
    }

    @Retryable(
            value = {DataIntegrityViolationException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    @Transactional
    public CartDTO removeProductFromCart(UUID cartId, UUID productId) {
        Cart cart = cartRepository.findByIdWithItems(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in cart"));

        cart.removeItem(item);
        cartRepository.save(cart);

        return modelMapper.map(cart, CartDTO.class);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalPrice(UUID cartId, String discountType) {
        Cart cart = cartRepository.findByIdWithItems(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        BigDecimal totalPrice = cart.getTotalPrice();
        int totalQuantity = cart.getItems().stream().mapToInt(CartItem::getQuantity).sum();
        return discountService.applyDiscount(totalPrice, totalQuantity, discountType);
    }
}
