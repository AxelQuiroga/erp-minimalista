package com.example.demo.application.usecase;

import com.example.demo.application.port.in.CreateCategoryPort;
import com.example.demo.domain.model.Category;
import com.example.demo.domain.repository.CategoryRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCategoryUseCase implements CreateCategoryPort {

    private final CategoryRepositoryPort categoryRepository;
    public CreateCategoryUseCase(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository= categoryRepository;
    }
    @Override
    @Transactional
    public Category createCategory(Category category) {
        // Regla de negocio: nombre único (opcional, pero bueno para ERP)
        if (categoryRepository.existsByName(category.getName())) {
            throw new com.example.demo.domain.exception.BusinessException(
                    "La categoría '" + category.getName() + "' ya existe"
            );
        }
        return categoryRepository.save(category);
    }

}
