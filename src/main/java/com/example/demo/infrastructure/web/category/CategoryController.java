package com.example.demo.infrastructure.web.category;

import com.example.demo.application.port.in.category.CreateCategoryPort;
import com.example.demo.application.port.in.category.DeactivateCategoryPort;
import com.example.demo.application.port.in.category.GetCategoryPort;
import com.example.demo.application.port.in.category.ListCategoriesPort;
import com.example.demo.application.port.in.category.UpdateCategoryPort;
import com.example.demo.domain.model.product.Category;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CreateCategoryPort createCategoryPort;
    private final GetCategoryPort getCategoryPort;
    private final ListCategoriesPort listCategoriesPort;
    private final UpdateCategoryPort updateCategoryPort;
    private final DeactivateCategoryPort deactivateCategoryPort;

    public CategoryController(CreateCategoryPort createCategoryPort,
                               GetCategoryPort getCategoryPort,
                               ListCategoriesPort listCategoriesPort,
                               UpdateCategoryPort updateCategoryPort,
                               DeactivateCategoryPort deactivateCategoryPort) {
        this.createCategoryPort = createCategoryPort;
        this.getCategoryPort = getCategoryPort;
        this.listCategoriesPort = listCategoriesPort;
        this.updateCategoryPort = updateCategoryPort;
        this.deactivateCategoryPort = deactivateCategoryPort;
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryRequestDTO request) {
        Category category = new Category(null, request.name(), true);
        Category saved = createCategoryPort.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(listCategoriesPort.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getCategoryPort.execute(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id,
                                           @Valid @RequestBody CategoryUpdateDTO dto) {
        Category updated = updateCategoryPort.execute(id, dto.getName());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        deactivateCategoryPort.execute(id);
        return ResponseEntity.noContent().build();
    }
}
