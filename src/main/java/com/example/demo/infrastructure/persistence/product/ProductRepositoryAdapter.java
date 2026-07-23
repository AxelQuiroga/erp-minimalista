package com.example.demo.infrastructure.persistence.product;

import com.example.demo.application.port.in.product.ProductFilter;
import com.example.demo.application.port.out.product.CategoryRepositoryPort;
import com.example.demo.domain.model.product.Category;
import com.example.demo.domain.model.product.Product;
import com.example.demo.application.port.out.product.ProductRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final SpringDataProductRepository repository;
    private final ProductMapper mapper;
    private final CategoryRepositoryPort categoryRepository;

    public ProductRepositoryAdapter(SpringDataProductRepository repository, ProductMapper mapper,
                                     CategoryRepositoryPort categoryRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        ProductEntity savedEntity = repository.save(entity);
        return enrichWithCategoryName(mapper.toDomain(savedEntity));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .map(this::enrichWithCategoryName);
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
        return enrichAll(repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList()));
    }

    @Override
    public List<Product> findByFilter(ProductFilter filter) {
        return enrichAll(repository.findByFilter(
                filter.q(),
                filter.minStock(),
                filter.active()
        ).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList()));
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long countByCurrentStockLessThan(int threshold) {
        return repository.countByCurrentStockLessThan(threshold);
    }

    // -- Helpers privados --

    private Product enrichWithCategoryName(Product product) {
        categoryRepository.findById(product.getCategoryId())
                .ifPresent(cat -> product.setCategoryName(cat.getName()));
        return product;
    }

    private List<Product> enrichAll(List<Product> products) {
        Map<Long, String> categoryMap = categoryRepository.findAll().stream()
                .collect(Collectors.toMap(Category::getId, Category::getName));
        products.forEach(p -> p.setCategoryName(categoryMap.get(p.getCategoryId())));
        return products;
    }
}
