package com.example.demo.application.usecase;

import com.example.demo.application.port.in.CreateCustomerPort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.model.Customer;
import com.example.demo.domain.repository.CustomerRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class CreateCustomerUseCase implements CreateCustomerPort {

    private final CustomerRepositoryPort customerRepository;

    public CreateCustomerUseCase(CustomerRepositoryPort customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer execute(String name, String email, String phone, String address) {
        if (customerRepository.existsByEmail(email)) {
            throw new BusinessException("El email '" + email + "' ya está registrado");
        }

        Customer customer = new Customer(null, name, email, phone, address, true, LocalDateTime.now());
        return customerRepository.save(customer);
    }
}
