package com.example.demo.infrastructure.persistence;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Getter @Setter // Acá usamos Lombok porque es puramente técnico
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long categoryId;
    private String name;
    private String sku;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private Integer currentStock;
}