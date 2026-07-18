package com.example.demo.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCustomerRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsByEmail(String email);
}
