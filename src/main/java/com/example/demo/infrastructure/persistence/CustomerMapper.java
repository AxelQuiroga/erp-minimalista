package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerEntity toEntity(Customer domain) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setEmail(domain.getEmail());
        entity.setPhone(domain.getPhone());
        entity.setAddress(domain.getAddress());
        entity.setActive(domain.isActive());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }

    public Customer toDomain(CustomerEntity entity) {
        return new Customer(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress(),
                entity.isActive(),
                entity.getCreatedAt()
        );
    }
}
