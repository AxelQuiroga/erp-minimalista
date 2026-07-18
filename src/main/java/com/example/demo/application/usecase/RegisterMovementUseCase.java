package com.example.demo.application.usecase;

import com.example.demo.application.port.in.RegisterMovementPort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.MovementType;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.model.StockMovement;
import com.example.demo.domain.repository.ProductRepositoryPort;
import com.example.demo.domain.repository.StockMovementRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterMovementUseCase implements RegisterMovementPort {

    private final StockMovementRepositoryPort movementRepository;
    private final ProductRepositoryPort productRepository;

    public RegisterMovementUseCase(StockMovementRepositoryPort movementRepository,
                                    ProductRepositoryPort productRepository) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
    }

    @Override
    public StockMovement execute(Long productId, Integer quantity, MovementType type, String reason) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + productId));

        if (!product.isActive()) {
            throw new BusinessException("No se puede registrar movimiento en un producto desactivado");
        }

        // Crear el movimiento según el tipo (valida reglas de cantidad en factory)
        StockMovement movement = createMovement(productId, quantity, type, reason);

        // Aplicar el efecto en el stock del producto
        applyToProduct(product, movement.getQuantity());

        // Persistir ambos en la misma transacción
        productRepository.save(product);
        return movementRepository.save(movement);
    }

    private StockMovement createMovement(Long productId, Integer quantity, MovementType type, String reason) {
        return switch (type) {
            case IN -> StockMovement.inbound(productId, quantity, reason);
            case OUT -> StockMovement.outbound(productId, quantity, reason);
            case ADJUSTMENT -> StockMovement.adjustment(productId, quantity, reason);
        };
    }

    private void applyToProduct(Product product, int signedQuantity) {
        if (signedQuantity >= 0) {
            product.addStock(signedQuantity);
        } else {
            product.removeStock(-signedQuantity);
        }
    }
}
