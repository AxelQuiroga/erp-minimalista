package com.example.demo.application.port.in;

import com.example.demo.domain.model.Sale;

public interface CancelSalePort {
    Sale execute(Long saleId, String reason);
}
