package com.example.demo.application.port.in.product;

public record ProductFilter(
        String q,
        Integer minStock,
        Boolean active
) {}
