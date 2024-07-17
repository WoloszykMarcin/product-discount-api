package com.apis.productdiscountapi.service;

import com.apis.productdiscountapi.model.Product;
import com.apis.productdiscountapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private UUID productId;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("Test Product");
        product.setPrice(BigDecimal.TEN);
        productId = UUID.randomUUID();
        product.setId(productId);
    }

    @Test
    void addProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.addProduct(product);

        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
        assertEquals(BigDecimal.TEN, createdProduct.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.getProductById(productId);

        assertNotNull(foundProduct);
        assertEquals(true, foundProduct.isPresent());
        assertEquals("Test Product", foundProduct.get().getName());
        verify(productRepository, times(1)).findById(productId);
    }
}
