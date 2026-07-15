package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.model.Category;
import com.example.demo.domain.repository.CategoryRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {

    private final SpringDataCategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryRepositoryAdapter(SpringDataCategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Category save(Category category) {
        // 1. Convertir Dominio -> Entidad
        CategoryEntity entity = mapper.toEntity(category);
        // 2. Guardar en JPA
        CategoryEntity savedEntity = repository.save(entity);
        // 3. Convertir Entidad -> Dominio
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
