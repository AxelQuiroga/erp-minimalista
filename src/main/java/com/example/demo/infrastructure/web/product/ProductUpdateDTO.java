package com.example.demo.infrastructure.web.product;

import com.example.demo.domain.model.product.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ProductUpdateDTO {

    @NotNull(message = "La categoría es obligatoria")
    private Long categoryId;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El SKU es obligatorio")
    private String sku;

    @NotNull(message = "El costo es obligatorio")
    @DecimalMin(value = "0.01", message = "El costo debe ser mayor a 0")
    private BigDecimal costPrice;

    @NotNull(message = "El precio de venta es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio de venta debe ser mayor a 0")
    private BigDecimal salePrice;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer currentStock;


    }
