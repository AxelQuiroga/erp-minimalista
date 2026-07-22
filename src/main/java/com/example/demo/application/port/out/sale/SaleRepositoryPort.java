package com.example.demo.application.port.out.sale;

import com.example.demo.application.port.in.sale.SaleFilter;
import com.example.demo.domain.model.sale.Sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SaleRepositoryPort {
    Sale save(Sale sale);
    Optional<Sale> findById(Long id);
    List<Sale> findAll();
    List<Sale> findByFilter(SaleFilter filter);
    long count();
    long countByCreatedAtAfter(LocalDateTime since);
    BigDecimal sumTotalAmountByCreatedAtAfter(LocalDateTime since);
    List<Sale> findTop5Recent();
}
