package com.example.demo.application.port.in;

import com.example.demo.domain.model.Category;

public interface CreateCategoryPort {
    Category createCategory(Category category);
}
