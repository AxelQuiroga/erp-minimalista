package com.example.demo.application.usecase.category;

import com.example.demo.application.port.in.category.GetCategoryPort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.product.Category;
import com.example.demo.application.port.out.product.CategoryRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class GetCategoryUseCase implements GetCategoryPort {

    private final CategoryRepositoryPort categoryRepository;

    public GetCategoryUseCase(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category execute(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
    }
}
