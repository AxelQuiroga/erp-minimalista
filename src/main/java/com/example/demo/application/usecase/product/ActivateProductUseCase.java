package com.example.demo.application.usecase.product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.application.port.in.product.ActivateProductPort;
import com.example.demo.application.port.out.product.ProductRepositoryPort;
import com.example.demo.domain.model.product.Product;


@Service
@Transactional
public class ActivateProductUseCase implements ActivateProductPort {
    
    private final ProductRepositoryPort productRepository;

    public ActivateProductUseCase(ProductRepositoryPort productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product execute(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

                product.activate();
        
        return productRepository.save(product);
    }
}
