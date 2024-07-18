package com.apis.productdiscountapi.exception.handler;

import com.apis.productdiscountapi.exception.CartNotFoundException;
import com.apis.productdiscountapi.exception.ProductAlreadyExistsException;
import com.apis.productdiscountapi.exception.ProductNotFoundException;
import com.apis.productdiscountapi.exception.constraints.ConstraintErrorHandler;
import lombok.Builder;
import lombok.Value;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, ConstraintErrorHandler> constraintErrorMapper;

    public GlobalExceptionHandler(Set<ConstraintErrorHandler> handlers) {
        this.constraintErrorMapper = handlers.stream().collect(Collectors.toMap(ConstraintErrorHandler::getConstraintName, Function.identity()));
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity handleProductAlreadyExistsException(ProductAlreadyExistsException exc) {
        return ResponseEntity.badRequest().body(new ProductAlreadyExistsException(exc.getName(), "PRODUCT_NAME_ALREADY_EXISTS"));
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(org.hibernate.exception.ConstraintViolationException exc) {
        String constraintName = exc.getConstraintName().substring(8, exc.getConstraintName().indexOf(" ") - 8);
        return ResponseEntity.badRequest().body(constraintErrorMapper.get(constraintName).mapToErrorDto());
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<String> handleFigureNotFoundException(CartNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleFigureNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException exc) {
        return ResponseEntity.badRequest().body(
                exc.getFieldErrors().stream().map(
                        fe -> new ValidationErrorDto(fe.getDefaultMessage(), fe.getField())
                ).collect(Collectors.toList())
        );
    }

    @ExceptionHandler(StaleObjectStateException.class)
    public ResponseEntity<String> handleObjectOptimisticLockingFailureException() {
        return new ResponseEntity<>("The product or cart has been modified by another user. Please refresh and try again.", HttpStatus.CONFLICT);
    }

    @Value
    public static class ValidationErrorDto {
        private String code;
        private String field;
    }

    @Value
    @Builder
    public static class ProductAlreadyExistsErrorDto {
        private String name;
        private String code;
    }

}
