package com.apis.productdiscountapi.exception.constraints;


import com.apis.productdiscountapi.exception.handler.GlobalExceptionHandler;

public interface ConstraintErrorHandler {
    GlobalExceptionHandler.ValidationErrorDto mapToErrorDto();

    String getConstraintName();
}
