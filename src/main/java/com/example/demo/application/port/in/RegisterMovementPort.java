package com.example.demo.application.port.in;

import com.example.demo.domain.model.MovementType;
import com.example.demo.domain.model.StockMovement;

public interface RegisterMovementPort {
    StockMovement execute(Long productId, Integer quantity, MovementType type, String reason);
}
