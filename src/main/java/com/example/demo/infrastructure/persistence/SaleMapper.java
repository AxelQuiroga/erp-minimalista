package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.model.Sale;
import com.example.demo.domain.model.SaleDetail;
import com.example.demo.domain.model.SaleStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SaleMapper {

    public SaleEntity toEntity(Sale domain) {
        SaleEntity entity = new SaleEntity();
        entity.setId(domain.getId());
        entity.setCustomerId(domain.getCustomerId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setStatus(domain.getStatus() != null ? domain.getStatus().name() : null);
        entity.setPaymentMethod(domain.getPaymentMethod());
        entity.setNotes(domain.getNotes());
        entity.setTotalAmount(domain.getTotalAmount());
        entity.setCancellationReason(domain.getCancellationReason());

        // Mapear items con la referencia bidireccional
        if (domain.getItems() != null) {
            entity.setItems(domain.getItems().stream()
                    .map(detail -> toDetailEntity(detail, entity))
                    .collect(Collectors.toList()));
        }

        return entity;
    }

    private SaleDetailEntity toDetailEntity(SaleDetail domain, SaleEntity parent) {
        SaleDetailEntity entity = new SaleDetailEntity();
        entity.setId(domain.getId());
        entity.setSale(parent);
        entity.setProductId(domain.getProductId());
        entity.setQuantity(domain.getQuantity());
        entity.setUnitPrice(domain.getUnitPrice());
        return entity;
    }

    public Sale toDomain(SaleEntity entity) {
        return new Sale(
                entity.getId(),
                entity.getCustomerId(),
                entity.getItems().stream()
                        .map(this::toDetailDomain)
                        .collect(Collectors.toList()),
                entity.getCreatedAt(),
                entity.getStatus() != null ? SaleStatus.valueOf(entity.getStatus()) : null,
                entity.getPaymentMethod(),
                entity.getNotes(),
                entity.getCancellationReason()
        );
    }

    private SaleDetail toDetailDomain(SaleDetailEntity entity) {
        return new SaleDetail(
                entity.getId(),
                entity.getSale().getId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getUnitPrice()
        );
    }
}
