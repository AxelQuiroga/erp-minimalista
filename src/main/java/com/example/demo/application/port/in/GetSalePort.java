package com.example.demo.application.port.in;

import com.example.demo.domain.model.Sale;

public interface GetSalePort {
    Sale execute(Long id);
}
