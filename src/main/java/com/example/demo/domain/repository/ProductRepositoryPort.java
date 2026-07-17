package com.example.demo.domain.repository;

import com.example.demo.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product product);
    Optional<Product> findById(Long id);
    boolean existsBySku(String sku);
    boolean hasActiveProductsByCategory(Long categoryId);
    List<Product> findAll();
}
