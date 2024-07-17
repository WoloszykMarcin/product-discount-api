package com.apis.productdiscountapi.mappings;

import com.apis.productdiscountapi.dto.ProductDTO;
import com.apis.productdiscountapi.model.Product;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class ProductToProductDTOConverter implements Converter<Product, ProductDTO> {

    @Override
    public ProductDTO convert(MappingContext<Product, ProductDTO> context) {
        Product source = context.getSource();
        return ProductDTO.builder()
                .id(source.getId())
                .name(source.getName())
                .price(source.getPrice())
                .build();
    }
}
