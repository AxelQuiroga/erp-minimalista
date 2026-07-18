package com.example.demo.application.port.in;

import com.example.demo.domain.model.Product;

import java.math.BigDecimal;

public interface UpdateProductPort {
    Product execute(Long id, String name, String sku, BigDecimal costPrice, BigDecimal salePrice, Integer currentStock);
}
