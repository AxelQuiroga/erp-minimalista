package com.example.demo.infrastructure.persistence.stock;

import com.example.demo.domain.model.stock.StockMovement;
import com.example.demo.application.port.out.stock.StockMovementRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class StockMovementRepositoryAdapter implements StockMovementRepositoryPort {

    private final SpringDataStockMovementRepository repository;
    private final StockMovementMapper mapper;

    public StockMovementRepositoryAdapter(SpringDataStockMovementRepository repository,
                                           StockMovementMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public StockMovement save(StockMovement movement) {
        StockMovementEntity entity = mapper.toEntity(movement);
        StockMovementEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Page<StockMovement> findByProductId(Long productId, Pageable pageable) {
        return repository.findByProductIdOrderByCreatedAtDesc(productId, pageable)
                .map(mapper::toDomain);
    }
}
