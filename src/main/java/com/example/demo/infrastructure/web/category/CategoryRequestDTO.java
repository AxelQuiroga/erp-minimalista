package com.example.demo.infrastructure.web.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
        @NotBlank(message = "El nombre es obligatorio") String name
) {}