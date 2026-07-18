package com.example.demo.domain.model.product;

import com.example.demo.domain.exception.BusinessException;

public class Category {
    private Long id;
    private String name;
    private boolean active;

    public Category(Long id, String name, boolean active) {
        validate(name);
        this.id = id;
        this.name = name;
        this.active = active;
    }

    private void validate(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");
    }

    public void rename(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");
        this.name = name;
    }

    public void deactivate() {
        if (!this.active) {
            throw new BusinessException("La categoría ya está desactivada");
        }
        this.active = false;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public boolean isActive() { return active; }
}
