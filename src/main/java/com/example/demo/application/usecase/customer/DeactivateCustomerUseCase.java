package com.example.demo.application.usecase.customer;

import com.example.demo.application.port.in.customer.DeactivateCustomerPort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.customer.Customer;
import com.example.demo.application.port.out.customer.CustomerRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeactivateCustomerUseCase implements DeactivateCustomerPort {

    private final CustomerRepositoryPort customerRepository;

    public DeactivateCustomerUseCase(CustomerRepositoryPort customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer execute(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: " + id));

        customer.deactivate();

        return customerRepository.save(customer);
    }
}
