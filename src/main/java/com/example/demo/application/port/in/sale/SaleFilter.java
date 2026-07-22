package com.example.demo.application.port.in.sale;

import java.time.LocalDate;

public record SaleFilter(
        String status,
        LocalDate from,
        LocalDate to
) {}
