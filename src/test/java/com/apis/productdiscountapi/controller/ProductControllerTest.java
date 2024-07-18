package com.apis.productdiscountapi.controller;

import com.apis.productdiscountapi.command.CreateProductCommand;
import com.apis.productdiscountapi.dto.ProductDTO;
import com.apis.productdiscountapi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

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

    private String uniqueProductName;

    @BeforeEach
    void setup() {
        uniqueProductName = "Test Product " + UUID.randomUUID();
    }

    @Test
    void createProduct() throws Exception {
        CreateProductCommand command = new CreateProductCommand(uniqueProductName, BigDecimal.valueOf(100.00));

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(uniqueProductName))
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
        CreateProductCommand command = new CreateProductCommand(uniqueProductName, BigDecimal.valueOf(100.00));
        ProductDTO createdProduct = productService.addProduct(command);

        mockMvc.perform(get("/api/products/" + createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdProduct.getId().toString()))
                .andExpect(jsonPath("$.name").value(uniqueProductName))
                .andExpect(jsonPath("$.price").value(100.00));
    }
}
