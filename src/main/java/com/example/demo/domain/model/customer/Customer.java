package com.example.demo.domain.model.customer;

import com.example.demo.domain.exception.BusinessException;

import java.time.LocalDateTime;

public class Customer {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private boolean active;
    private final LocalDateTime createdAt;

    public Customer(Long id, String name, String email, String phone, String address,
                    boolean active, LocalDateTime createdAt) {
        validate(name, email);
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.active = active;
        this.createdAt = createdAt;
    }

    private void validate(String name, String email) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("El email es obligatorio");
        if (!email.contains("@") || !email.contains("."))
            throw new IllegalArgumentException("Email inválido: " + email);
    }

    public void rename(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");
        this.name = name;
    }

    public void changeEmail(String email) {
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("El email es obligatorio");
        if (!email.contains("@") || !email.contains("."))
            throw new IllegalArgumentException("Email inválido: " + email);
        this.email = email;
    }

    public void changePhone(String phone) {
        this.phone = phone;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void deactivate() {
        if (!this.active) {
            throw new BusinessException("El cliente ya está desactivado");
        }
        this.active = false;
    }

    public void activate() {
    if (this.active) {
        throw new BusinessException("El cliente ya está activo");
    }
    this.active = true;
}
    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
