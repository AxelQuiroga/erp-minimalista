package com.example.demo.application.usecase.category;

import com.example.demo.application.port.in.category.ListCategoriesPort;
import com.example.demo.domain.model.product.Category;
import com.example.demo.application.port.out.product.CategoryRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCategoriesUseCase implements ListCategoriesPort {

    private final CategoryRepositoryPort categoryRepository;

    public ListCategoriesUseCase(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> execute() {
        return categoryRepository.findAll();
    }
}
