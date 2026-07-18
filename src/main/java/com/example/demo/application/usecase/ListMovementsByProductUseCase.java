package com.example.demo.application.usecase;

import com.example.demo.application.port.in.ListMovementsByProductPort;
import com.example.demo.domain.model.StockMovement;
import com.example.demo.domain.repository.StockMovementRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ListMovementsByProductUseCase implements ListMovementsByProductPort {

    private final StockMovementRepositoryPort movementRepository;

    public ListMovementsByProductUseCase(StockMovementRepositoryPort movementRepository) {
        this.movementRepository = movementRepository;
    }

    @Override
    public Page<StockMovement> execute(Long productId, Pageable pageable) {
        return movementRepository.findByProductId(productId, pageable);
    }
}
