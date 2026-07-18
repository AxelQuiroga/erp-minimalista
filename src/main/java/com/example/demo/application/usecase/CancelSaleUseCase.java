package com.example.demo.application.usecase;

import com.example.demo.application.port.in.CancelSalePort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.*;
import com.example.demo.domain.repository.ProductRepositoryPort;
import com.example.demo.domain.repository.SaleRepositoryPort;
import com.example.demo.domain.repository.StockMovementRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CancelSaleUseCase implements CancelSalePort {

    private final SaleRepositoryPort saleRepository;
    private final ProductRepositoryPort productRepository;
    private final StockMovementRepositoryPort stockMovementRepository;

    public CancelSaleUseCase(SaleRepositoryPort saleRepository,
                              ProductRepositoryPort productRepository,
                              StockMovementRepositoryPort stockMovementRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Override
    public Sale execute(Long saleId, String reason) {
        // 1. Validar venta existe
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new NotFoundException("Venta no encontrada: " + saleId));

        // 2. Cancelar (valida en dominio que esté COMPLETED)
        sale.cancel(reason);

        // 3. Persistir venta cancelada
        saleRepository.save(sale);

        // 4. Devolver stock y crear movimiento IN por cada item
        for (SaleDetail detail : sale.getItems()) {
            Product product = productRepository.findById(detail.getProductId())
                    .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + detail.getProductId()));
            product.addStock(detail.getQuantity());
            productRepository.save(product);

            StockMovement movement = StockMovement.inbound(
                    detail.getProductId(),
                    detail.getQuantity(),
                    "Anulación venta #" + saleId
            );
            stockMovementRepository.save(movement);
        }

        return sale;
    }
}
