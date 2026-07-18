package com.example.demo.application.port.in;

import com.example.demo.domain.model.Customer;

import java.util.List;

public interface ListCustomersPort {
    List<Customer> execute();
}
