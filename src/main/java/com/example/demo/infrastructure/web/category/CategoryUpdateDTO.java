package com.example.demo.infrastructure.web.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryUpdateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;
}
