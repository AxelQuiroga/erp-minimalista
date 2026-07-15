package com.example.demo.domain.model;

public class Category {
    private Long id;
    private String name;

    public Category(Long id, String name) {
        validate(name);
        this.id = id;
        this.name = name;
    }

    private void validate(String value)  {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }


}
