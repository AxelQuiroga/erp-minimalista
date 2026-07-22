package com.example.demo.application.port.in.customer;

import com.example.demo.domain.model.customer.Customer;

public interface UpdateCustomerPort {
    Customer execute(Long id, String name, String email, String phone, String address, boolean active);
}
