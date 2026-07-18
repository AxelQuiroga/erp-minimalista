package com.example.demo.application.port.in;

import com.example.demo.domain.model.Customer;

public interface CreateCustomerPort {
    Customer execute(String name, String email, String phone, String address);
}
