package com.example.demo.application.port.in;

import com.example.demo.domain.model.Category;

public interface UpdateCategoryPort {
    Category execute(Long id, String name);
}
