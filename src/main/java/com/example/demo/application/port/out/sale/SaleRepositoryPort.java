package com.example.demo.application.port.out.sale;

import com.example.demo.domain.model.sale.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleRepositoryPort {
    Sale save(Sale sale);
    Optional<Sale> findById(Long id);
    List<Sale> findAll();
}
