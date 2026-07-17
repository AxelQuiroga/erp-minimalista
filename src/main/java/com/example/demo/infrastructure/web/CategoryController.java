package com.example.demo.infrastructure.web;

import com.example.demo.application.usecase.*;
import com.example.demo.domain.model.Category;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryUseCase getCategoryUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeactivateCategoryUseCase deactivateCategoryUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase,
                               GetCategoryUseCase getCategoryUseCase,
                               ListCategoriesUseCase listCategoriesUseCase,
                               UpdateCategoryUseCase updateCategoryUseCase,
                               DeactivateCategoryUseCase deactivateCategoryUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.getCategoryUseCase = getCategoryUseCase;
        this.listCategoriesUseCase = listCategoriesUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.deactivateCategoryUseCase = deactivateCategoryUseCase;
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryRequestDTO request) {
        Category category = new Category(null, request.name(), true);
        Category saved = createCategoryUseCase.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(listCategoriesUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getCategoryUseCase.execute(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id,
                                           @Valid @RequestBody CategoryUpdateDTO dto) {
        Category updated = updateCategoryUseCase.execute(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        deactivateCategoryUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
