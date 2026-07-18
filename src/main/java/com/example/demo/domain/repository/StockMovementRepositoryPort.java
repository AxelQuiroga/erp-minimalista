package com.example.demo.domain.repository;

import com.example.demo.domain.model.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockMovementRepositoryPort {
    StockMovement save(StockMovement movement);
    Page<StockMovement> findByProductId(Long productId, Pageable pageable);
}
