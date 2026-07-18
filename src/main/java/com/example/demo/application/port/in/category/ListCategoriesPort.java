package com.example.demo.application.port.in.category;

import com.example.demo.domain.model.product.Category;

import java.util.List;

public interface ListCategoriesPort {
    List<Category> execute();
}
