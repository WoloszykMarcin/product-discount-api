package com.apis.productdiscountapi.mappings;

import com.apis.productdiscountapi.dto.CartDTO;
import com.apis.productdiscountapi.dto.CartItemDTO;
import com.apis.productdiscountapi.dto.ProductDTO;
import com.apis.productdiscountapi.model.Cart;
import com.apis.productdiscountapi.model.CartItem;
import com.apis.productdiscountapi.model.Product;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CartToCartDTOConverter implements Converter<Cart, CartDTO> {

    @Override
    public CartDTO convert(MappingContext<Cart, CartDTO> mappingContext) {
        Cart source = mappingContext.getSource();
        return CartDTO.builder()
                .id(source.getId())
                .items(source.getItems().stream()
                        .map(this::convertCartItem)
                        .collect(Collectors.toList()))
                .totalPrice(source.getTotalPrice())
                .build();
    }

    private CartItemDTO convertCartItem(CartItem cartItem) {
        return CartItemDTO.builder()
                .id(cartItem.getId())
                .product(convertProduct(cartItem.getProduct()))
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getTotalPrice())
                .build();
    }

    private ProductDTO convertProduct(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
