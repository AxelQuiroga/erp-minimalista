package com.example.demo.application.usecase.customer;

import com.example.demo.application.port.in.customer.UpdateCustomerPort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.customer.Customer;
import com.example.demo.application.port.out.customer.CustomerRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateCustomerUseCase implements UpdateCustomerPort {

    private final CustomerRepositoryPort customerRepository;

    public UpdateCustomerUseCase(CustomerRepositoryPort customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer execute(Long id, String name, String email, String phone, String address) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: " + id));

        // Validar email único si cambió
        if (!customer.getEmail().equals(email) && customerRepository.existsByEmail(email)) {
            throw new BusinessException("El email '" + email + "' ya está registrado");
        }

        customer.rename(name);
        customer.changeEmail(email);
        customer.changePhone(phone);
        customer.changeAddress(address);

        return customerRepository.save(customer);
    }
}
