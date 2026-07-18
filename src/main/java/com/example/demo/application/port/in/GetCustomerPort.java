package com.example.demo.application.port.in;

import com.example.demo.domain.model.Customer;

public interface GetCustomerPort {
    Customer execute(Long id);
}
