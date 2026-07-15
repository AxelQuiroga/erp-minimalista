package com.example.demo.domain.model;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private String sku;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private Integer currentStock;

    // CONSTRUCTOR: El guardián de la integridad
    public Product(Long id, String name, String sku, BigDecimal costPrice, BigDecimal salePrice, Integer currentStock) {
        validate(name, sku, costPrice, salePrice, currentStock);
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.currentStock = currentStock;
    }

    private void validate(String name, String sku, BigDecimal cost, BigDecimal sale, Integer stock) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        if (sku == null || sku.isBlank()) throw new IllegalArgumentException("El SKU es obligatorio");
        if (cost == null || cost.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("El costo debe ser mayor a 0");
        if (sale == null || sale.compareTo(cost) < 0) throw new IllegalArgumentException("El precio de venta no puede ser menor al costo");
        if (stock == null || stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo");
    }

    // AHORA: Los Setters no son simples, deben validar también.
    public void setSalePrice(BigDecimal newPrice) {
        if (newPrice.compareTo(this.costPrice) < 0) throw new IllegalArgumentException("Nuevo precio menor al costo");
        this.salePrice = newPrice;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }
    public BigDecimal getCostPrice() {
        return costPrice;
    }
    public BigDecimal getSalePrice() {
        return salePrice;
    }
    public Integer getCurrentStock() {
        return currentStock;
    }
    // Getters... (Sin setters para los campos críticos si no queremos permitir cambios)
}
