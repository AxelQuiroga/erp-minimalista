package com.example.demo.application.port.out.stock;

import com.example.demo.domain.model.stock.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockMovementRepositoryPort {
    StockMovement save(StockMovement movement);
    Page<StockMovement> findByProductId(Long productId, Pageable pageable);
}
