package com.example.demo.application.port.in;

import com.example.demo.domain.model.Sale;

import java.math.BigDecimal;
import java.util.List;

public interface CreateSalePort {

    record SaleItemCommand(Long productId, Integer quantity, BigDecimal unitPrice) {}
    record CreateSaleCommand(Long customerId, List<SaleItemCommand> items, String paymentMethod, String notes) {}

    Sale execute(CreateSaleCommand command);
}
