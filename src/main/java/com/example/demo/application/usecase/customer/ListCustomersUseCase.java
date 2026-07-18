package com.example.demo.application.usecase.customer;

import com.example.demo.application.port.in.customer.ListCustomersPort;
import com.example.demo.domain.model.customer.Customer;
import com.example.demo.application.port.out.customer.CustomerRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCustomersUseCase implements ListCustomersPort {

    private final CustomerRepositoryPort customerRepository;

    public ListCustomersUseCase(CustomerRepositoryPort customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> execute() {
        return customerRepository.findAll();
    }
}
