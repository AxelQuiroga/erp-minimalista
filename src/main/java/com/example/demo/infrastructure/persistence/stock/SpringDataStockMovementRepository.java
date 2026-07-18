package com.example.demo.infrastructure.persistence.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataStockMovementRepository extends JpaRepository<StockMovementEntity, Long> {

    Page<StockMovementEntity> findByProductIdOrderByCreatedAtDesc(Long productId, Pageable pageable);
}
