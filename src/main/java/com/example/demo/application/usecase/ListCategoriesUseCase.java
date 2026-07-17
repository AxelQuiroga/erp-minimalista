package com.example.demo.application.usecase;

import com.example.demo.domain.model.Category;
import com.example.demo.domain.repository.CategoryRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCategoriesUseCase {

    private final CategoryRepositoryPort categoryRepository;

    public ListCategoriesUseCase(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> execute() {
        return categoryRepository.findAll();
    }
}
