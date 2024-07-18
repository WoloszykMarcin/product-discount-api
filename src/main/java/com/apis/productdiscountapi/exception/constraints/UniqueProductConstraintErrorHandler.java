package com.apis.productdiscountapi.exception.constraints;

import com.apis.productdiscountapi.exception.handler.GlobalExceptionHandler;
import org.springframework.stereotype.Service;

@Service
public class UniqueProductConstraintErrorHandler implements ConstraintErrorHandler {
    @Override
    public GlobalExceptionHandler.ValidationErrorDto mapToErrorDto() {
        return new GlobalExceptionHandler.ValidationErrorDto("PRODUCT_NAME_NOT_UNIQUE", "product");
    }

    @Override
    public String getConstraintName() {
        return "UC_PRODUCT_NAME";
    }
}
