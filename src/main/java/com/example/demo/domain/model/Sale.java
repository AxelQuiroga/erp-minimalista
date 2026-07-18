package com.example.demo.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Sale {

    private Long id;
    private final Long customerId;
    private final List<SaleDetail> items;
    private final LocalDateTime createdAt;
    private String status;
    private String paymentMethod;
    private String notes;
    private final BigDecimal totalAmount;

    public Sale(Long id, Long customerId, List<SaleDetail> items, LocalDateTime createdAt,
                String status, String paymentMethod, String notes) {
        if (customerId == null) throw new IllegalArgumentException("El cliente es obligatorio");
        if (items == null || items.isEmpty()) throw new IllegalArgumentException("La venta debe tener al menos un item");
        if (status == null || status.isBlank()) throw new IllegalArgumentException("El estado es obligatorio");

        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.createdAt = createdAt;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.notes = notes;
        this.totalAmount = items.stream()
                .map(SaleDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Getters
    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public List<SaleDetail> getItems() { return items; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getNotes() { return notes; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}
