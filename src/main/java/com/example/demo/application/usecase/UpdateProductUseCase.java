package com.example.demo.application.usecase;

import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.repository.CategoryRepositoryPort;
import com.example.demo.domain.repository.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateProductUseCase {
    private final ProductRepositoryPort productRepository;
    private final CategoryRepositoryPort categoryRepository;

    public UpdateProductUseCase(ProductRepositoryPort productRepository,
                                CategoryRepositoryPort categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product execute(Long id, Product updatedData) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Producto no encontrado"));

        // Validar SKU único (excepto para sí mismo)
        if (!product.getSku().equals(updatedData.getSku())
                && productRepository.existsBySku(updatedData.getSku())) {
            throw new BusinessException("SKU duplicado");
        }

        // Validar categoría existente
        if (!categoryRepository.existsById(updatedData.getCategoryId())) {
            throw new BusinessException("Categoría no encontrada");
        }

        product.rename(updatedData.getName());
        product.changeSku(updatedData.getSku());
        product.updatePrice(updatedData.getCostPrice(), updatedData.getSalePrice());
        product.updateStock(updatedData.getCurrentStock());

        return productRepository.save(product);
    }
}