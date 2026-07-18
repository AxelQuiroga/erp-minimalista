package com.example.demo.application.usecase;

import com.example.demo.application.port.in.UpdateCategoryPort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.model.Category;
import com.example.demo.domain.repository.CategoryRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateCategoryUseCase implements UpdateCategoryPort {

    private final CategoryRepositoryPort categoryRepository;

    public UpdateCategoryUseCase(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category execute(Long id, String name) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoría no encontrada"));

        category.rename(name);

        return categoryRepository.save(category);
    }
}
