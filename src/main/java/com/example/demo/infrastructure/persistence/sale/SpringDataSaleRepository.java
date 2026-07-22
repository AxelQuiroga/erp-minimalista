package com.example.demo.infrastructure.persistence.sale;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface SpringDataSaleRepository extends JpaRepository<SaleEntity, Long> {

    @Query("SELECT s FROM SaleEntity s WHERE " +
           "(:status IS NULL OR s.status = :status) AND " +
           "(:from IS NULL OR s.createdAt >= :from) AND " +
           "(:to IS NULL OR s.createdAt <= :to) " +
           "ORDER BY s.createdAt DESC")
    List<SaleEntity> findByFilter(@Param("status") String status,
                                  @Param("from") LocalDateTime from,
                                  @Param("to") LocalDateTime to);

    long countByCreatedAtAfter(LocalDateTime since);

    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM SaleEntity s WHERE s.createdAt >= :since")
    BigDecimal sumTotalAmountByCreatedAtAfter(@Param("since") LocalDateTime since);

    @Query("SELECT s FROM SaleEntity s ORDER BY s.createdAt DESC")
    List<SaleEntity> findTop5Recent(Pageable pageable);
}
