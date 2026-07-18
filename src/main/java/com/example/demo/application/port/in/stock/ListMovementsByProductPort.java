package com.example.demo.application.port.in.stock;

import com.example.demo.domain.model.stock.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListMovementsByProductPort {
    Page<StockMovement> execute(Long productId, Pageable pageable);
}
