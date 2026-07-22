package com.example.demo.infrastructure.web.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerUpdateDTO(
        @NotBlank(message = "El nombre es obligatorio") String name,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Email inválido")
        String email,

        String phone,
        String address,

        @NotNull(message = "El estado es obligatorio") Boolean active
) {}
