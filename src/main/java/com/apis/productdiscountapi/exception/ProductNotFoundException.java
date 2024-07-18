package com.apis.productdiscountapi.exception;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Value
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
    private final UUID id;

    public ProductNotFoundException(UUID id) {
        super("Product not found with id: " + id);
        this.id = id;
    }
}
