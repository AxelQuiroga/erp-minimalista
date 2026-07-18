package com.example.demo.application.port.in;

import com.example.demo.domain.model.Category;

import java.util.List;

public interface ListCategoriesPort {
    List<Category> execute();
}
