package com.example.demo.infrastructure.persistence.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataCustomerRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT c FROM CustomerEntity c WHERE " +
           "(:q IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(c.email) LIKE LOWER(CONCAT('%', :q, '%'))) AND " +
           "(:active IS NULL OR c.active = :active)")
    List<CustomerEntity> findByFilter(@Param("q") String q,
                                      @Param("active") Boolean active);
}
