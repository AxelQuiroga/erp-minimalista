package com.example.demo.application.port.in.sale;

import com.example.demo.domain.model.sale.Sale;

import java.util.List;

public interface ListSalesPort {
    List<Sale> execute();
    List<Sale> execute(SaleFilter filter);
}
