package com.example.demo.application.port.out.product;

import com.example.demo.application.port.in.product.ProductFilter;
import com.example.demo.domain.model.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product product);
    Optional<Product> findById(Long id);
    boolean existsBySku(String sku);
    boolean hasActiveProductsByCategory(Long categoryId);
    List<Product> findAll();
    List<Product> findByFilter(ProductFilter filter);
    long count();
    long countByCurrentStockLessThan(int threshold);
}
