package com.example.demo.domain.model.product;

import com.example.demo.domain.exception.BusinessException;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private final Long categoryId;
    private String name;
    private String sku;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private Integer currentStock;
    private boolean active;
    // CONSTRUCTOR: El guardián de la integridad
    public Product(Long id, Long categoryId  ,String name, String sku, BigDecimal costPrice, BigDecimal salePrice, Integer currentStock, boolean active) {
        validate(name, sku, costPrice, salePrice, currentStock, categoryId);

        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.sku = sku;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.currentStock = currentStock;
        this.active = active;
    }

    private void validate(String name, String sku, BigDecimal cost, BigDecimal sale, Integer stock, Long categoryId) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        if (sku == null || sku.isBlank()) throw new IllegalArgumentException("El SKU es obligatorio");
        if (cost == null || cost.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("El costo debe ser mayor a 0");
        if (sale == null || sale.compareTo(cost) < 0) throw new IllegalArgumentException("El precio de venta no puede ser menor al costo");
        if (stock == null || stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo");
        if (categoryId == null) throw new IllegalArgumentException("La categoría es obligatoria");

        // ...
    }

    // AHORA: Los Setters no son simples, deben validar también.
    public void setSalePrice(BigDecimal newPrice) {
        if (newPrice.compareTo(this.costPrice) < 0) throw new IllegalArgumentException("Nuevo precio menor al costo");
        this.salePrice = newPrice;
    }

    public Long getId() {
        return id;
    }

    public Long getCategoryId() {
        return categoryId;
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

    public boolean isActive() {
        return active;
    }
    public void deactivate() {
        if (!this.active) {
            throw new BusinessException("El producto ya está desactivado");
        }
        this.active = false;
    }
    public void rename(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        this.name = name;
    }

    public void changeSku(String sku) {
        if (sku == null || sku.isBlank()) throw new IllegalArgumentException("El SKU es obligatorio");
        this.sku = sku;
    }

    public void updatePrice(BigDecimal newCost, BigDecimal newSale) {
        if (newCost.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("El costo debe ser mayor a 0");
        if (newSale.compareTo(newCost) < 0) throw new IllegalArgumentException("El precio de venta no puede ser menor al costo");
        this.costPrice = newCost;
        this.salePrice = newSale;
    }

    public void updateStock(Integer stock) {
        if (stock == null || stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo");
        this.currentStock = stock;
    }

    public void addStock(Integer quantity) {
        if (quantity == null || quantity <= 0) throw new IllegalArgumentException("La cantidad debe ser positiva");
        this.currentStock += quantity;
    }

    public void removeStock(Integer quantity) {
        if (quantity == null || quantity <= 0) throw new IllegalArgumentException("La cantidad debe ser positiva");
        if (this.currentStock < quantity) {
            throw new BusinessException("Stock insuficiente: disponible " + this.currentStock + ", requerido " + quantity);
        }
        this.currentStock -= quantity;
    }

    // Getters... (Sin setters para los campos críticos si no queremos permitir cambios)
}
