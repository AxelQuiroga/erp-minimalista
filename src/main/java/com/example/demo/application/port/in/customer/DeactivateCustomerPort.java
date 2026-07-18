package com.example.demo.application.port.in.customer;

import com.example.demo.domain.model.customer.Customer;

public interface DeactivateCustomerPort {
    Customer execute(Long id);
}
