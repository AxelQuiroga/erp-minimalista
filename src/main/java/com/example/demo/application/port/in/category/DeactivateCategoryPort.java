package com.example.demo.application.port.in.category;

import com.example.demo.domain.model.product.Category;

public interface DeactivateCategoryPort {
    Category execute(Long id);
}
