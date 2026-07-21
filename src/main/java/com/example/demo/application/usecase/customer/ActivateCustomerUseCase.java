package com.example.demo.application.usecase.customer;

import com.example.demo.application.port.in.customer.ActivateCustomerPort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.customer.Customer;
import com.example.demo.application.port.out.customer.CustomerRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivateCustomerUseCase implements ActivateCustomerPort {

    private final CustomerRepositoryPort customerRepository;

    public ActivateCustomerUseCase(CustomerRepositoryPort customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer execute(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: " + id));

        customer.activate();

        return customerRepository.save(customer);
    }
}