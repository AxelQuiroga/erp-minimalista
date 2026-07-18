package com.example.demo.application.port.in;

import com.example.demo.domain.model.Sale;

import java.util.List;

public interface ListSalesPort {
    List<Sale> execute();
}
