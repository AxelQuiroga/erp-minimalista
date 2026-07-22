package com.example.demo.infrastructure.persistence.sale;

import com.example.demo.application.port.in.sale.SaleFilter;
import com.example.demo.domain.model.sale.Sale;
import com.example.demo.application.port.out.sale.SaleRepositoryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Override
    public List<Sale> findByFilter(SaleFilter filter) {
        LocalDateTime from = filter.from() != null ? filter.from().atStartOfDay() : null;
        LocalDateTime to = filter.to() != null ? filter.to().atTime(23, 59, 59) : null;
        return repository.findByFilter(filter.status(), from, to).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long countByCreatedAtAfter(LocalDateTime since) {
        return repository.countByCreatedAtAfter(since);
    }

    @Override
    public BigDecimal sumTotalAmountByCreatedAtAfter(LocalDateTime since) {
        return repository.sumTotalAmountByCreatedAtAfter(since);
    }

    @Override
    public List<Sale> findTop5Recent() {
        return repository.findTop5Recent(Pageable.ofSize(5)).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
