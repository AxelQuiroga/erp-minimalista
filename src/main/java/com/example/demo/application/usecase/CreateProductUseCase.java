package com.example.demo.application.usecase;

import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.repository.CategoryRepositoryPort;
import com.example.demo.domain.repository.ProductRepositoryPort;
import com.example.demo.domain.service.ProductServicePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateProductUseCase implements ProductServicePort {

    private final ProductRepositoryPort productRepository;
    private final CategoryRepositoryPort categoryRepository;

    public CreateProductUseCase(ProductRepositoryPort productRepository, CategoryRepositoryPort categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional // Importante: la transacción vive en el caso de uso
    public Product createProduct(Product product) {
        // 1. Reglas de negocio que cruzan fronteras (DB)
        validateSkuUnique(product.getSku());
        validateCategoryExists(product.getCategoryId());

        // 2. El dominio ya se validó a sí mismo al instanciarse (invariantes)
        // 3. Guardamos
        return productRepository.save(product);
    }

    private void validateSkuUnique(String sku) {
        if (productRepository.existsBySku(sku)) {
            throw new BusinessException("SKU duplicado: " + sku);
        }
    }

    private void validateCategoryExists(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new BusinessException("Categoría no encontrada: " + categoryId);
        }
    }
}