package com.example.demo.application.port.in.product;

import com.example.demo.domain.model.product.Product;

public interface DeactivateProductPort {
    Product execute(Long id);
}
