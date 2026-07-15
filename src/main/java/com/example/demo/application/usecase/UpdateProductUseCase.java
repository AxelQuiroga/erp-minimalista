package com.example.demo.application.usecase;

import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.repository.CategoryRepositoryPort;
import com.example.demo.domain.repository.ProductRepositoryPort;
import com.example.demo.infrastructure.web.ProductUpdateDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateProductUseCase {

    private final ProductRepositoryPort productRepository;

    public UpdateProductUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(Long id, ProductUpdateDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Producto no encontrado"));

        // Validar SKU único (excluyendo el producto actual)
        if (!product.getSku().equals(dto.getSku())
                && productRepository.existsBySku(dto.getSku())) {
            throw new BusinessException("SKU duplicado: " + dto.getSku());
        }

        // Aplicar cambios usando los métodos semánticos del dominio
        product.rename(dto.getName());
        product.changeSku(dto.getSku());
        product.updatePrice(dto.getCostPrice(), dto.getSalePrice());
        product.updateStock(dto.getCurrentStock());

        return productRepository.save(product);
    }
}