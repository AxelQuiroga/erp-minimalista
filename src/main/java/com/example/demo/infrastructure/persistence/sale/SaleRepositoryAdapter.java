package com.example.demo.infrastructure.persistence.sale;

import com.example.demo.domain.model.sale.Sale;
import com.example.demo.application.port.out.sale.SaleRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SaleRepositoryAdapter implements SaleRepositoryPort {

    private final SpringDataSaleRepository repository;
    private final SaleMapper mapper;

    public SaleRepositoryAdapter(SpringDataSaleRepository repository, SaleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Sale save(Sale sale) {
        SaleEntity entity = mapper.toEntity(sale);
        SaleEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Sale> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Sale> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
