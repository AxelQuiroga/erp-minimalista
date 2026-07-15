package com.example.demo.domain.service;

import com.example.demo.domain.model.Product;

public interface ProductServicePort {
    Product createProduct(Product product);
}