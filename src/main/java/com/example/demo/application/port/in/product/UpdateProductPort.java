package com.example.demo.application.port.in.product;

import com.example.demo.domain.model.product.Product;

import java.math.BigDecimal;

public interface UpdateProductPort {
    Product execute(Long id, String name, String sku, BigDecimal costPrice, BigDecimal salePrice, Integer currentStock, boolean active);
}
