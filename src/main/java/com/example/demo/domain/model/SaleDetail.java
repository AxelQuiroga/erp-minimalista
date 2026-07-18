package com.example.demo.domain.model;

import java.math.BigDecimal;

public class SaleDetail {

    private Long id;
    private Long saleId;
    private final Long productId;
    private final Integer quantity;
    private final BigDecimal unitPrice;

    public SaleDetail(Long id, Long saleId, Long productId, Integer quantity, BigDecimal unitPrice) {
        if (productId == null) throw new IllegalArgumentException("El producto es obligatorio");
        if (quantity == null || quantity <= 0) throw new IllegalArgumentException("La cantidad debe ser positiva");
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("El precio unitario debe ser mayor a 0");
        this.id = id;
        this.saleId = saleId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // Getters
    public Long getId() { return id; }
    public Long getSaleId() { return saleId; }
    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
}
