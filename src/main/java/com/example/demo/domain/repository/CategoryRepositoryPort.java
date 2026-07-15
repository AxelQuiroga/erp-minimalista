package com.example.demo.domain.repository;

import com.example.demo.domain.model.Category;

import java.util.Optional;

public interface CategoryRepositoryPort {
    Category save(Category category);
    Optional<Category> findById(Long id);
    boolean existsById(Long id);
    boolean existsByName(String name);// <--- ESTE ES EL CLAVE PARA PRODUCTO
}
