package com.example.demo.application.usecase.category;

import com.example.demo.application.port.in.category.UpdateCategoryPort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.product.Category;
import com.example.demo.application.port.out.product.CategoryRepositoryPort;
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
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        category.rename(name);

        return categoryRepository.save(category);
    }
}
