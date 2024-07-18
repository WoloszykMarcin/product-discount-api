package com.apis.productdiscountapi.exception;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Value
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartNotFoundException extends RuntimeException {
    private final UUID id;

    public CartNotFoundException(UUID id) {
        super("Cart not found with id: " + id);
        this.id = id;
    }
}
