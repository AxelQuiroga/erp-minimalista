package com.example.demo.application.port.in;

import com.example.demo.domain.model.Customer;

public interface UpdateCustomerPort {
    Customer execute(Long id, String name, String email, String phone, String address);
}
