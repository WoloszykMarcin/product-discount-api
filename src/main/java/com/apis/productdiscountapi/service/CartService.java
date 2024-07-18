package com.apis.productdiscountapi.service;

import com.apis.productdiscountapi.dto.CartDTO;
import com.apis.productdiscountapi.exception.CartNotFoundException;
import com.apis.productdiscountapi.exception.ProductNotFoundException;
import com.apis.productdiscountapi.model.Cart;
import com.apis.productdiscountapi.model.CartItem;
import com.apis.productdiscountapi.model.Product;
import com.apis.productdiscountapi.repository.CartRepository;
import com.apis.productdiscountapi.repository.ProductRepository;
import org.hibernate.StaleObjectStateException;
import org.modelmapper.ModelMapper;
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

    @Transactional
    public CartDTO addProductToCart(UUID cartId, UUID productId, int quantity) {
        Cart cart = cartRepository.findByIdWithItems(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        Cart finalCart = cart;
        existingItemOpt.ifPresentOrElse(
                item -> item.setQuantity(item.getQuantity() + quantity),
                () -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(finalCart);
                    newItem.setProduct(product);
                    newItem.setQuantity(quantity);
                    finalCart.addItem(newItem);
                }
        );

        try {
            cart = cartRepository.save(cart);
        } catch (StaleObjectStateException e) {
            throw new IllegalArgumentException("The cart is being modified by another user, please try again!", e);
        }

        return modelMapper.map(cart, CartDTO.class);
    }


    @Transactional
    public CartDTO removeProductFromCart(UUID cartId, UUID productId) {
        Cart cart = cartRepository.findByIdWithItems(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(productId));

        cart.removeItem(item);

        try {
            cart = cartRepository.save(cart);
        } catch (StaleObjectStateException e) {
            throw new IllegalArgumentException("The cart is being modified by another user, please try again!", e);
        }

        return modelMapper.map(cart, CartDTO.class);
    }


    @Transactional(readOnly = true)
    public BigDecimal getTotalPrice(UUID cartId, String discountType) {
        Cart cart = cartRepository.findByIdWithItems(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
        BigDecimal totalPrice = cart.getTotalPrice();
        int totalQuantity = cart.getItems().stream().mapToInt(CartItem::getQuantity).sum();
        return discountService.applyDiscount(totalPrice, totalQuantity, discountType);
    }
}

