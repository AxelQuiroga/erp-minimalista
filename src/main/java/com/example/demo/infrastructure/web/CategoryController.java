package com.example.demo.infrastructure.web;

import com.example.demo.domain.model.Category;
import com.example.demo.domain.service.CategoryServicePort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryServicePort categoryService;

    public CategoryController(CategoryServicePort categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryRequestDTO request) {
        Category category = new Category(null, request.name());
        Category saved = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}