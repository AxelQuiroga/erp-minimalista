package com.example.demo.application.port.in.stock;

import com.example.demo.domain.model.stock.MovementType;
import com.example.demo.domain.model.stock.StockMovement;

public interface RegisterMovementPort {
    StockMovement execute(Long productId, Integer quantity, MovementType type, String reason);
}
