package com.example.demo.application.usecase.product;

import com.example.demo.application.port.in.product.DeactivateProductPort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.product.Product;
import com.example.demo.application.port.out.product.ProductRepositoryPort;
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