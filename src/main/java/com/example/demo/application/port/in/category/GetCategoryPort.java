package com.example.demo.application.port.in.category;

import com.example.demo.domain.model.product.Category;

public interface GetCategoryPort {
    Category execute(Long id);
}
