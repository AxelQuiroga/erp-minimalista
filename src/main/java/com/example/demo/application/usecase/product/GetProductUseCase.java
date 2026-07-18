package com.example.demo.application.usecase.product;

import com.example.demo.application.port.in.product.GetProductPort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.product.Product;
import com.example.demo.application.port.out.product.ProductRepositoryPort;
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