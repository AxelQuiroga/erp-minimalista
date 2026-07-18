package com.example.demo.infrastructure.web.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerUpdateDTO(
        @NotBlank(message = "El nombre es obligatorio") String name,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Email inválido")
        String email,

        String phone,
        String address
) {}
