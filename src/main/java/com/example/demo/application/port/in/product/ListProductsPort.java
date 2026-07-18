package com.example.demo.application.port.in.product;

import com.example.demo.domain.model.product.Product;

import java.util.List;

public interface ListProductsPort {
    List<Product> execute();
}
