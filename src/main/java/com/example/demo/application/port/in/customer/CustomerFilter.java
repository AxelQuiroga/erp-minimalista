package com.example.demo.application.port.in.customer;

public record CustomerFilter(
        String q,
        Boolean active
) {}
