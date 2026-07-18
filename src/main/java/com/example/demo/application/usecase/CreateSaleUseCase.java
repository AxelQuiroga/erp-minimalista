package com.example.demo.application.usecase;

import com.example.demo.application.port.in.CreateSalePort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.*;
import com.example.demo.domain.repository.CustomerRepositoryPort;
import com.example.demo.domain.repository.ProductRepositoryPort;
import com.example.demo.domain.repository.SaleRepositoryPort;
import com.example.demo.domain.repository.StockMovementRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CreateSaleUseCase implements CreateSalePort {

    private final CustomerRepositoryPort customerRepository;
    private final ProductRepositoryPort productRepository;
    private final SaleRepositoryPort saleRepository;
    private final StockMovementRepositoryPort stockMovementRepository;

    public CreateSaleUseCase(CustomerRepositoryPort customerRepository,
                              ProductRepositoryPort productRepository,
                              SaleRepositoryPort saleRepository,
                              StockMovementRepositoryPort stockMovementRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Override
    public Sale execute(CreateSaleCommand command) {
        // 1. Validar cliente
        Customer customer = customerRepository.findById(command.customerId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: " + command.customerId()));
        if (!customer.isActive()) {
            throw new BusinessException("El cliente '" + customer.getName() + "' está desactivado");
        }

        // 2. Validar productos, stock y capturar detalles
        List<SaleDetail> details = new ArrayList<>();
        List<Product> productsToUpdate = new ArrayList<>();

        for (CreateSalePort.SaleItemCommand item : command.items()) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + item.productId()));
            if (!product.isActive()) {
                throw new BusinessException("El producto '" + product.getName() + "' está desactivado");
            }

            // Validar y descontar stock (lanza BusinessException si no alcanza)
            product.removeStock(item.quantity());
            productsToUpdate.add(product);

            details.add(new SaleDetail(null, null, product.getId(), item.quantity(), item.unitPrice()));
        }

        // 3. Crear y guardar la venta
        Sale sale = new Sale(null, command.customerId(), details, LocalDateTime.now(),
                SaleStatus.COMPLETED, command.paymentMethod(), command.notes(), null);
        Sale savedSale = saleRepository.save(sale);

        // 4. Persistir cambios de stock
        for (Product product : productsToUpdate) {
            productRepository.save(product);
        }

        // 5. Crear movimiento de stock OUT para cada item
        for (SaleDetail detail : savedSale.getItems()) {
            StockMovement movement = StockMovement.outbound(
                    detail.getProductId(),
                    detail.getQuantity(),
                    "Venta #" + savedSale.getId()
            );
            stockMovementRepository.save(movement);
        }

        return savedSale;
    }
}
