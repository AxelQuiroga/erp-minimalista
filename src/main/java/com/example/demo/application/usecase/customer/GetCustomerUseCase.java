package com.example.demo.application.usecase.customer;

import com.example.demo.application.port.in.customer.GetCustomerPort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.customer.Customer;
import com.example.demo.application.port.out.customer.CustomerRepositoryPort;
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
