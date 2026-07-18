package com.example.demo.domain.model;

import com.example.demo.domain.exception.BusinessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Sale {

    private Long id;
    private final Long customerId;
    private final List<SaleDetail> items;
    private final LocalDateTime createdAt;
    private SaleStatus status;
    private String paymentMethod;
    private String notes;
    private final BigDecimal totalAmount;
    private String cancellationReason;

    public Sale(Long id, Long customerId, List<SaleDetail> items, LocalDateTime createdAt,
                SaleStatus status, String paymentMethod, String notes, String cancellationReason) {
        if (customerId == null) throw new IllegalArgumentException("El cliente es obligatorio");
        if (items == null || items.isEmpty()) throw new IllegalArgumentException("La venta debe tener al menos un item");
        if (status == null) throw new IllegalArgumentException("El estado es obligatorio");

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
        this.cancellationReason = cancellationReason;
    }

    public void cancel(String reason) {
        if (this.status != SaleStatus.COMPLETED) {
            throw new BusinessException("Solo se pueden cancelar ventas en estado COMPLETED");
        }
        this.status = SaleStatus.CANCELLED;
        this.cancellationReason = reason;
    }

    // Getters
    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public List<SaleDetail> getItems() { return items; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public SaleStatus getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getNotes() { return notes; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getCancellationReason() { return cancellationReason; }
}
