package com.apis.productdiscountapi.repository;

import com.apis.productdiscountapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
