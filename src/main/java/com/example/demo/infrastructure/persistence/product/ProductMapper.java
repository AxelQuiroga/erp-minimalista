package com.example.demo.infrastructure.persistence.product;

import com.example.demo.domain.model.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // Convierte el objeto de Dominio (Puro) a Entidad JPA (Para la base)
    public ProductEntity toEntity(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setCategoryId(product.getCategoryId());
        entity.setName(product.getName());
        entity.setSku(product.getSku());
        entity.setCostPrice(product.getCostPrice());
        entity.setSalePrice(product.getSalePrice());
        entity.setCurrentStock(product.getCurrentStock());
        entity.setActive(product.isActive());
        return entity;
    }

    // Convierte la Entidad JPA (Base) a Objeto de Dominio (Puro)
    public Product toDomain(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getCategoryId(),
                entity.getName(),
                entity.getSku(),
                entity.getCostPrice(),
                entity.getSalePrice(),
                entity.getCurrentStock(),
                entity.isActive()
        );
    }
}