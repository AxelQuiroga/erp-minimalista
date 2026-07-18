package com.example.demo.application.port.in;

import com.example.demo.domain.model.Category;

public interface GetCategoryPort {
    Category execute(Long id);
}
