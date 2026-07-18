package com.example.demo.infrastructure.web.stock;

import com.example.demo.domain.model.stock.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RegisterMovementRequest(
        @NotNull(message = "El producto es obligatorio")
        Long productId,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        Integer quantity,

        @NotNull(message = "El tipo de movimiento es obligatorio")
        MovementType type,

        String reason
) {}
