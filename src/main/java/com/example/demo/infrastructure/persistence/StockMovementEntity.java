package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.model.MovementType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movement")
@Getter @Setter
public class StockMovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String reason;
}
