package com.example.demo.infrastructure.persistence.sale;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataSaleRepository extends JpaRepository<SaleEntity, Long> {
}
