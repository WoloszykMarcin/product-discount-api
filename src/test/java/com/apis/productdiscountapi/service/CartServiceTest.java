package com.apis.productdiscountapi.service;

import com.apis.productdiscountapi.dto.CartDTO;
import com.apis.productdiscountapi.model.Cart;
import com.apis.productdiscountapi.model.CartItem;
import com.apis.productdiscountapi.model.Product;
import com.apis.productdiscountapi.repository.CartRepository;
import com.apis.productdiscountapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.retry.annotation.EnableRetry;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableRetry
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DiscountService discountService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CartService cartService;

    private Cart cart;
    private Product product;
    private UUID cartId;
    private UUID productId;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        product = new Product();
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100.00));
        productId = UUID.randomUUID();
        product.setId(productId);
        cartId = UUID.randomUUID();
        cart.setId(cartId);
    }

    @Test
    void addProductToCart() {
        when(cartRepository.findByIdWithItems(cartId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(modelMapper.map(any(Cart.class), eq(CartDTO.class))).thenReturn(new CartDTO());

        CartDTO updatedCart = cartService.addProductToCart(cartId, productId, 3);

        assertNotNull(updatedCart);
        verify(cartRepository, times(1)).findByIdWithItems(cartId);
        verify(productRepository, times(1)).findById(productId);
        verify(cartRepository, times(1)).save(any(Cart.class));
        assertEquals(1, cart.getItems().size());
        assertEquals(3, cart.getItems().iterator().next().getQuantity());

        updatedCart = cartService.addProductToCart(cartId, productId, 2);

        assertNotNull(updatedCart);
        verify(cartRepository, times(2)).findByIdWithItems(cartId);
        verify(productRepository, times(2)).findById(productId);
        verify(cartRepository, times(2)).save(any(Cart.class));
        assertEquals(1, cart.getItems().size());
        assertEquals(5, cart.getItems().iterator().next().getQuantity());
    }

    @Test
    void removeProductFromCart() {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(3);
        cart.addItem(cartItem);

        when(cartRepository.findByIdWithItems(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(modelMapper.map(any(Cart.class), eq(CartDTO.class))).thenReturn(new CartDTO());

        CartDTO updatedCart = cartService.removeProductFromCart(cartId, productId);

        assertNotNull(updatedCart);
        verify(cartRepository, times(1)).findByIdWithItems(cartId);
        verify(cartRepository, times(1)).save(any(Cart.class));
        assertEquals(0, cart.getItems().size());
    }

    @Test
    void getTotalPrice() {
        when(cartRepository.findByIdWithItems(cartId)).thenReturn(Optional.of(cart));
        when(discountService.applyDiscount(any(BigDecimal.class), anyInt(), anyString())).thenReturn(BigDecimal.TEN);

        BigDecimal totalPrice = cartService.getTotalPrice(cartId, "AMOUNT");

        assertNotNull(totalPrice);
        assertEquals(BigDecimal.TEN, totalPrice);
        verify(cartRepository, times(1)).findByIdWithItems(cartId);
        verify(discountService, times(1)).applyDiscount(any(BigDecimal.class), anyInt(), anyString());
    }
}
