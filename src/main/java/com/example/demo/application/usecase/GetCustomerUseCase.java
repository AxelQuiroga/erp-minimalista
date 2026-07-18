package com.example.demo.application.usecase;

import com.example.demo.application.port.in.GetCustomerPort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.Customer;
import com.example.demo.domain.repository.CustomerRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class GetCustomerUseCase implements GetCustomerPort {

    private final CustomerRepositoryPort customerRepository;

    public GetCustomerUseCase(CustomerRepositoryPort customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer execute(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: " + id));
    }
}
