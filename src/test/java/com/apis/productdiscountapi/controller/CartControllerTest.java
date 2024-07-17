package com.apis.productdiscountapi.controller;

import com.apis.productdiscountapi.dto.CartDTO;
import com.apis.productdiscountapi.model.Product;
import com.apis.productdiscountapi.service.CartService;
import com.apis.productdiscountapi.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    private Product product;
    private CartDTO cart;

    @BeforeEach
    void setup() {
        product = new Product();
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100.00));
        product = productService.addProduct(product);

        cart = cartService.createCart();
    }

    @Test
    void createCart() throws Exception {
        mockMvc.perform(post("/api/carts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void getCartById() throws Exception {
        mockMvc.perform(get("/api/carts/" + cart.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cart.getId().toString()));
    }

    @Test
    void addProductToCart() throws Exception {
        mockMvc.perform(post("/api/carts/" + cart.getId() + "/products")
                        .param("productId", product.getId().toString())
                        .param("quantity", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items[0].product.id").value(product.getId().toString()))
                .andExpect(jsonPath("$.items[0].quantity").value(3));
    }

    @Test
    void removeProductFromCart() throws Exception {
        cartService.addProductToCart(cart.getId(), product.getId(), 3);

        mockMvc.perform(delete("/api/carts/" + cart.getId() + "/products")
                        .param("productId", product.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isEmpty());
    }

    @Test
    void getTotalPrice() throws Exception {
        cartService.addProductToCart(cart.getId(), product.getId(), 3);

        mockMvc.perform(get("/api/carts/" + cart.getId() + "/totalPrice")
                        .param("discountType", "AMOUNT")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
