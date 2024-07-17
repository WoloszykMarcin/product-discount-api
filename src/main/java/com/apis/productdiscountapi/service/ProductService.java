package com.apis.productdiscountapi.service;

import com.apis.productdiscountapi.command.CreateProductCommand;
import com.apis.productdiscountapi.dto.ProductDTO;
import com.apis.productdiscountapi.model.Product;
import com.apis.productdiscountapi.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ProductDTO addProduct(CreateProductCommand productCommand) {
        Product product = modelMapper.map(productCommand, Product.class);
        product.setId(UUID.randomUUID());
        return modelMapper.map(productRepository.save(product), ProductDTO.class);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(UUID id) {
        return productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductDTO.class));
    }
}
