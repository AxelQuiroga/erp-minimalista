package com.example.demo.application.usecase;

import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.model.Category;
import com.example.demo.domain.repository.CategoryRepositoryPort;
import com.example.demo.domain.repository.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeactivateCategoryUseCase {

    private final CategoryRepositoryPort categoryRepository;
    private final ProductRepositoryPort productRepository;

    public DeactivateCategoryUseCase(CategoryRepositoryPort categoryRepository,
                                      ProductRepositoryPort productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public Category execute(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoría no encontrada"));

        if (productRepository.hasActiveProductsByCategory(id)) {
            throw new BusinessException("No se puede desactivar la categoría: tiene productos activos");
        }

        category.deactivate();

        return categoryRepository.save(category);
    }
}
