package com.example.demo.application.usecase;

import com.example.demo.application.port.in.CreateProductPort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.Category;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.repository.CategoryRepositoryPort;
import com.example.demo.domain.repository.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CreateProductUseCase implements CreateProductPort {

    private final ProductRepositoryPort productRepository;
    private final CategoryRepositoryPort categoryRepository;

    public CreateProductUseCase(ProductRepositoryPort productRepository, CategoryRepositoryPort categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        validateSkuUnique(product.getSku());
        validateCategoryActive(product.getCategoryId());

        return productRepository.save(product);
    }

    private void validateSkuUnique(String sku) {
        if (productRepository.existsBySku(sku)) {
            throw new BusinessException("SKU duplicado: " + sku);
        }
    }

    private void validateCategoryActive(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new NotFoundException("Categoría no encontrada: " + categoryId);
        }
        if (!category.get().isActive()) {
            throw new BusinessException("La categoría '" + category.get().getName() + "' está desactivada");
        }
    }
}