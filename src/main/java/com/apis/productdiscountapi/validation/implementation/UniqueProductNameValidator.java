package com.apis.productdiscountapi.validation.implementation;


import com.apis.productdiscountapi.repository.ProductRepository;
import com.apis.productdiscountapi.validation.annotation.UniqueProductName;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {

    private final ProductRepository productRepository;

    public UniqueProductNameValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !productRepository.existsByName(name);
    }
}
