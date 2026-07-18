package com.example.demo.application.usecase;

import com.example.demo.application.port.in.DeactivateProductPort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.repository.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeactivateProductUseCase implements DeactivateProductPort {

    private final ProductRepositoryPort productRepository;

    public DeactivateProductUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        product.deactivate(); // Llama al método del dominio que valida si ya está desactivado

        return productRepository.save(product);
    }
}