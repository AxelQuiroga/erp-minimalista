package com.example.demo.domain.model;

import java.time.LocalDateTime;

public class StockMovement {

    private final Long id;
    private final Long productId;
    private final Integer quantity;      // firmado: + suma stock, - resta stock
    private final MovementType type;
    private final LocalDateTime createdAt;
    private final String reason;

    private StockMovement(Long id, Long productId, Integer quantity, MovementType type,
                          LocalDateTime createdAt, String reason) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.type = type;
        this.createdAt = createdAt;
        this.reason = reason;
    }

    // -- Factory methods semánticos --

    public static StockMovement inbound(Long productId, int quantity, String reason) {
        if (quantity <= 0) throw new IllegalArgumentException("La cantidad de ingreso debe ser positiva");
        if (productId == null) throw new IllegalArgumentException("El producto es obligatorio");
        return new StockMovement(null, productId, quantity, MovementType.IN, null, reason);
    }

    public static StockMovement outbound(Long productId, int quantity, String reason) {
        if (quantity <= 0) throw new IllegalArgumentException("La cantidad de egreso debe ser positiva");
        if (productId == null) throw new IllegalArgumentException("El producto es obligatorio");
        return new StockMovement(null, productId, -quantity, MovementType.OUT, null, reason);
    }

    public static StockMovement adjustment(Long productId, int quantity, String reason) {
        if (quantity == 0) throw new IllegalArgumentException("La cantidad de ajuste no puede ser cero");
        if (productId == null) throw new IllegalArgumentException("El producto es obligatorio");
        return new StockMovement(null, productId, quantity, MovementType.ADJUSTMENT, null, reason);
    }

    // -- Reconstrucción desde BD (sin validaciones de negocio) --

    public static StockMovement reconstitute(Long id, Long productId, Integer quantity, MovementType type,
                                              LocalDateTime createdAt, String reason) {
        return new StockMovement(id, productId, quantity, type, createdAt, reason);
    }

    // -- Getters --

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public MovementType getType() { return type; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getReason() { return reason; }

    public int getAbsoluteQuantity() {
        return Math.abs(quantity);
    }
}
