package com.example.demo.application.usecase;

import com.example.demo.application.port.in.GetProductPort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.repository.ProductRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class GetProductUseCase implements GetProductPort {
    private final ProductRepositoryPort productRepository;

    public GetProductUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
    }
}