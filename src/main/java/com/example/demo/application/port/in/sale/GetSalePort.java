package com.example.demo.application.port.in.sale;

import com.example.demo.domain.model.sale.Sale;

public interface GetSalePort {
    Sale execute(Long id);
}
