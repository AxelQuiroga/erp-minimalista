package com.example.demo.infrastructure.persistence.category;

import com.example.demo.domain.model.product.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryEntity toEntity(Category category) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(category.getId());
        entity.setName(category.getName());
        entity.setActive(category.isActive());
        return entity;
    }

    public Category toDomain(CategoryEntity entity) {
        return new Category(
                entity.getId(),
                entity.getName(),
                entity.isActive()
        );
    }
}
