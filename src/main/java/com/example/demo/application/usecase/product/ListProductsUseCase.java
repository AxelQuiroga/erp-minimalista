package com.example.demo.application.usecase.product;

import com.example.demo.application.port.in.product.ListProductsPort;
import com.example.demo.application.port.in.product.ProductFilter;
import com.example.demo.domain.model.product.Product;
import com.example.demo.application.port.out.product.ProductRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProductsUseCase implements ListProductsPort {

    private final ProductRepositoryPort productRepository;

    public ListProductsUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> execute() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> execute(ProductFilter filter) {
        return productRepository.findByFilter(filter);
    }
}
