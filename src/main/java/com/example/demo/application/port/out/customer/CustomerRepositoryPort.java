package com.example.demo.application.port.out.customer;

import com.example.demo.application.port.in.customer.CustomerFilter;
import com.example.demo.domain.model.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    boolean existsByEmail(String email);
    List<Customer> findAll();
    List<Customer> findByFilter(CustomerFilter filter);
    long count();
}
