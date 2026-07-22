package com.example.demo.infrastructure.persistence.sale;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHint;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SpringDataSaleRepository extends JpaRepository<SaleEntity, Long> {

    @Query("SELECT DISTINCT s FROM SaleEntity s LEFT JOIN FETCH s.items WHERE " +
           "(:status IS NULL OR s.status = :status) AND " +
           "(:from IS NULL OR s.createdAt >= :from) AND " +
           "(:toEnd IS NULL OR s.createdAt < :toEnd) " +
           "ORDER BY s.createdAt DESC")
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH, value = "false"))
    List<SaleEntity> findByFilter(@Param("status") String status,
                                  @Param("from") LocalDateTime from,
                                  @Param("toEnd") LocalDateTime toEnd);

    @Query("SELECT COUNT(s) FROM SaleEntity s WHERE s.createdAt >= :since")
    long countByCreatedAtAfter(@Param("since") LocalDateTime since);

    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM SaleEntity s WHERE s.createdAt >= :since")
    BigDecimal sumTotalAmountByCreatedAtAfter(@Param("since") LocalDateTime since);

    @Query("SELECT s FROM SaleEntity s ORDER BY s.createdAt DESC")
    List<SaleEntity> findTop5Recent(Pageable pageable);

    @Query("SELECT s FROM SaleEntity s LEFT JOIN FETCH s.items WHERE s.id = :id")
    Optional<SaleEntity> findByIdWithItems(@Param("id") Long id);

    @Query("SELECT DISTINCT s FROM SaleEntity s LEFT JOIN FETCH s.items ORDER BY s.createdAt DESC")
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH, value = "false"))
    List<SaleEntity> findAllWithItems();
}
