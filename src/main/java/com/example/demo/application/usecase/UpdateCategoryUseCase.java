package com.example.demo.application.usecase;

import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.model.Category;
import com.example.demo.domain.repository.CategoryRepositoryPort;
import com.example.demo.infrastructure.web.CategoryUpdateDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateCategoryUseCase {

    private final CategoryRepositoryPort categoryRepository;

    public UpdateCategoryUseCase(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category execute(Long id, CategoryUpdateDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoría no encontrada"));

        category.rename(dto.getName());

        return categoryRepository.save(category);
    }
}
