package com.example.demo.infrastructure.persistence.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsBySku(String sku);
    boolean existsByCategoryIdAndActiveTrue(Long categoryId);

    @Query("SELECT p FROM ProductEntity p WHERE " +
           "(:q IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :q, '%'))) AND " +
           "(:minStock IS NULL OR p.currentStock >= :minStock) AND " +
           "(:active IS NULL OR p.active = :active)")
    List<ProductEntity> findByFilter(@Param("q") String q,
                                     @Param("minStock") Integer minStock,
                                     @Param("active") Boolean active);

    long countByCurrentStockLessThan(int threshold);
}
