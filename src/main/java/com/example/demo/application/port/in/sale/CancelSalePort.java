package com.example.demo.application.port.in.sale;

import com.example.demo.domain.model.sale.Sale;

public interface CancelSalePort {
    Sale execute(Long saleId, String reason);
}
