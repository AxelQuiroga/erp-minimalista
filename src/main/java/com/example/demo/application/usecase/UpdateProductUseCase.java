package com.example.demo.application.usecase;

import com.example.demo.application.port.in.UpdateProductPort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.Product;
import com.example.demo.domain.repository.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class UpdateProductUseCase implements UpdateProductPort {

    private final ProductRepositoryPort productRepository;

    public UpdateProductUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product execute(Long id, String name, String sku, BigDecimal costPrice, BigDecimal salePrice, Integer currentStock) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        // Validar SKU único (excluyendo el producto actual)
        if (!product.getSku().equals(sku)
                && productRepository.existsBySku(sku)) {
            throw new BusinessException("SKU duplicado: " + sku);
        }

        // Aplicar cambios usando los métodos semánticos del dominio
        product.rename(name);
        product.changeSku(sku);
        product.updatePrice(costPrice, salePrice);
        product.updateStock(currentStock);

        return productRepository.save(product);
    }
}