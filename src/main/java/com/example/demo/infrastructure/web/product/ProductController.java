package com.example.demo.infrastructure.web.product;

import com.example.demo.application.port.in.product.CreateProductPort;
import com.example.demo.application.port.in.product.DeactivateProductPort;
import com.example.demo.application.port.in.product.GetProductPort;
import com.example.demo.application.port.in.product.ListProductsPort;
import com.example.demo.application.port.in.product.UpdateProductPort;
import com.example.demo.domain.model.product.Product;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final CreateProductPort createProductPort;
    private final GetProductPort getProductPort;
    private final ListProductsPort listProductsPort;
    private final UpdateProductPort updateProductPort;
    private final DeactivateProductPort deactivateProductPort;

    public ProductController(CreateProductPort createProductPort,
                             GetProductPort getProductPort,
                             ListProductsPort listProductsPort,
                             UpdateProductPort updateProductPort,
                             DeactivateProductPort deactivateProductPort) {
        this.createProductPort = createProductPort;
        this.getProductPort = getProductPort;
        this.listProductsPort = listProductsPort;
        this.updateProductPort = updateProductPort;
        this.deactivateProductPort = deactivateProductPort;
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequestDTO dto) {
        Product product = dto.toDomain();
        Product created = createProductPort.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(listProductsPort.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getProductPort.execute(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id,
                                          @Valid @RequestBody ProductUpdateDTO dto) {
        Product updated = updateProductPort.execute(
                id, dto.getName(), dto.getSku(), dto.getCostPrice(),
                dto.getSalePrice(), dto.getCurrentStock()
        );
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        deactivateProductPort.execute(id);
        return ResponseEntity.noContent().build();
    }
}