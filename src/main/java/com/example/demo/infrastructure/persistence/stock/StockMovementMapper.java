package com.example.demo.infrastructure.persistence.stock;

import com.example.demo.domain.model.stock.StockMovement;
import org.springframework.stereotype.Component;

@Component
public class StockMovementMapper {

    public StockMovementEntity toEntity(StockMovement domain) {
        StockMovementEntity entity = new StockMovementEntity();
        entity.setId(domain.getId());
        entity.setProductId(domain.getProductId());
        entity.setQuantity(domain.getQuantity());
        entity.setMovementType(domain.getType());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setReason(domain.getReason());
        return entity;
    }

    public StockMovement toDomain(StockMovementEntity entity) {
        return StockMovement.reconstitute(
                entity.getId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getMovementType(),
                entity.getCreatedAt(),
                entity.getReason()
        );
    }
}
