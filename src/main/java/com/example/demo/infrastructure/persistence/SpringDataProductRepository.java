package com.example.demo.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Esta es la interfaz real de Spring Data que habla con Postgres
@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsBySku(String sku);
    boolean existsByCategoryIdAndActiveTrue(Long categoryId);
}
