package com.apis.productdiscountapi.service;

import com.apis.productdiscountapi.command.CreateProductCommand;
import com.apis.productdiscountapi.dto.ProductDTO;
import com.apis.productdiscountapi.model.Product;
import com.apis.productdiscountapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;
    private CreateProductCommand createProductCommand;
    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();

        product = new Product();
        product.setName("Test Product");
        product.setPrice(BigDecimal.TEN);
        product.setId(productId);

        productDTO = ProductDTO.builder()
                .id(productId)
                .name("Test Product")
                .price(BigDecimal.TEN)
                .build();

        createProductCommand = new CreateProductCommand("Test Product", BigDecimal.TEN);
    }

    @Test
    void addProduct() {
        when(modelMapper.map(any(CreateProductCommand.class), eq(Product.class))).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

        ProductDTO createdProduct = productService.addProduct(createProductCommand);

        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
        assertEquals(BigDecimal.TEN, createdProduct.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

        List<ProductDTO> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(modelMapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(productDTO);

        Optional<ProductDTO> foundProduct = productService.getProductById(productId);

        assertNotNull(foundProduct);
        assertEquals(true, foundProduct.isPresent());
        assertEquals("Test Product", foundProduct.get().getName());
        verify(productRepository, times(1)).findById(productId);
    }
}
