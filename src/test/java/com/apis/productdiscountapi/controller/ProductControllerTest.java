package com.apis.productdiscountapi.controller;

import com.apis.productdiscountapi.model.Product;
import com.apis.productdiscountapi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    @Test
    void createProduct() throws Exception {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100.00));

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(100.00));
    }

    @Test
    void getAllProducts() throws Exception {
        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getProductById() throws Exception {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100.00));
        Product createdProduct = productService.addProduct(product);

        mockMvc.perform(get("/api/products/" + createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdProduct.getId().toString()))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(100.00));
    }
}
