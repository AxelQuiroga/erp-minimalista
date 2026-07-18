package com.example.demo.application.usecase;

import com.example.demo.application.port.in.ListProductsPort;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.repository.ProductRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProductsUseCase implements ListProductsPort {
    private final ProductRepositoryPort productRepository;

    public ListProductsUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> execute() {
        return productRepository.findAll();
    }
}