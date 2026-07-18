package com.example.demo.infrastructure.web.sale;

import com.example.demo.application.port.in.sale.CreateSalePort;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CreateSaleRequest(
        @NotNull(message = "El cliente es obligatorio") Long customerId,

        @NotEmpty(message = "La venta debe tener al menos un item")
        @Valid
        List<SaleItemRequest> items,

        String paymentMethod,
        String notes
) {
    public record SaleItemRequest(
            @NotNull(message = "El producto es obligatorio") Long productId,
            @NotNull(message = "La cantidad es obligatoria") Integer quantity,
            @NotNull(message = "El precio unitario es obligatorio") BigDecimal unitPrice
    ) {}

    public CreateSalePort.CreateSaleCommand toCommand() {
        return new CreateSalePort.CreateSaleCommand(
                customerId(),
                items().stream()
                        .map(i -> new CreateSalePort.SaleItemCommand(i.productId(), i.quantity(), i.unitPrice()))
                        .toList(),
                paymentMethod(),
                notes()
        );
    }
}
