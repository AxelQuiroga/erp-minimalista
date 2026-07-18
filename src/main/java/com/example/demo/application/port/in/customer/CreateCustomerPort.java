package com.example.demo.application.port.in.customer;

import com.example.demo.domain.model.customer.Customer;

public interface CreateCustomerPort {
    Customer execute(String name, String email, String phone, String address);
}
