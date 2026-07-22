package com.example.demo.infrastructure.persistence.sale;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sale")
@Getter @Setter
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private List<SaleDetailEntity> items;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String status;

    private String paymentMethod;

    private String notes;

    private BigDecimal totalAmount;

    private String cancellationReason;
}
