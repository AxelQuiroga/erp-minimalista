package com.example.demo.application.port.out.product;

import com.example.demo.domain.model.product.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {
    Category save(Category category);
    Optional<Category> findById(Long id);
    boolean existsById(Long id);
    boolean existsByName(String name);
    List<Category> findAll();
}
