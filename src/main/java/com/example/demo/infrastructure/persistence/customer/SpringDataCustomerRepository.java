package com.example.demo.infrastructure.persistence.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCustomerRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsByEmail(String email);
}
