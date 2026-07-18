package com.example.demo.infrastructure.persistence.product;

import com.example.demo.domain.model.product.Product;
import com.example.demo.application.port.out.product.ProductRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final SpringDataProductRepository repository; // Este es el repo de JPA real
    private final ProductMapper mapper;
    public ProductRepositoryAdapter(SpringDataProductRepository repository,ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Product save(Product product) {
        // 1. Convertir Dominio -> Entidad (Mapper necesario)
        ProductEntity  entity = mapper.toEntity(product);
        // 2. Guardar en JPA
        ProductEntity savedEntity = repository.save(entity);
        // 3. Convertir Entidad -> Dominio

        return mapper.toDomain(savedEntity); // TODO: Implementar mapeo
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain); // TODO: Implementar
    }

    @Override
    public boolean existsBySku(String sku){
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
}