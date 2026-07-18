package com.example.demo.application.usecase;

import com.example.demo.application.port.in.DeactivateCustomerPort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.model.Customer;
import com.example.demo.domain.repository.CustomerRepositoryPort;
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
                .orElseThrow(() -> new BusinessException("Cliente no encontrado: " + id));

        customer.deactivate();

        return customerRepository.save(customer);
    }
}
