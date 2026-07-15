package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    // Convierte el objeto de Dominio (Puro) a Entidad JPA (Para la base)
    public CategoryEntity toEntity(Category category) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(category.getId());
        entity.setName(category.getName());
        return entity;
    }

    // Convierte la Entidad JPA (Base) a Objeto de Dominio (Puro)
    public Category toDomain(CategoryEntity entity) {
        return new Category(
                entity.getId(),
                entity.getName()
        );
    }
}
