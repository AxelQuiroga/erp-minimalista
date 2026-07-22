package com.example.demo.application.port.in.customer;

import com.example.demo.domain.model.customer.Customer;

import java.util.List;

public interface ListCustomersPort {
    List<Customer> execute();
    List<Customer> execute(CustomerFilter filter);
}
