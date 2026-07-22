package com.example.demo.infrastructure.persistence.product;

import com.example.demo.application.port.in.product.ProductFilter;
import com.example.demo.domain.model.product.Product;
import com.example.demo.application.port.out.product.ProductRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final SpringDataProductRepository repository;
    private final ProductMapper mapper;

    public ProductRepositoryAdapter(SpringDataProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        ProductEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsBySku(String sku) {
        return repository.existsBySku(sku);
    }

    @Override
    public boolean hasActiveProductsByCategory(Long categoryId) {
        return repository.existsByCategoryIdAndActiveTrue(categoryId);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByFilter(ProductFilter filter) {
        return repository.findByFilter(
                filter.q(),
                filter.minStock(),
                filter.active()
        ).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long countByCurrentStockLessThan(int threshold) {
        return repository.countByCurrentStockLessThan(threshold);
    }
}
