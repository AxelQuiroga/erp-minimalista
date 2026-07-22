package com.example.demo.infrastructure.web.product;

import com.example.demo.application.port.in.product.ActivateProductPort;
import com.example.demo.application.port.in.product.CreateProductPort;
import com.example.demo.application.port.in.product.DeactivateProductPort;
import com.example.demo.application.port.in.product.GetProductPort;
import com.example.demo.application.port.in.product.ListProductsPort;
import com.example.demo.application.port.in.product.ProductFilter;
import com.example.demo.application.port.in.product.UpdateProductPort;
import com.example.demo.domain.model.product.Product;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final CreateProductPort createProductPort;
    private final GetProductPort getProductPort;
    private final ListProductsPort listProductsPort;
    private final UpdateProductPort updateProductPort;
    private final DeactivateProductPort deactivateProductPort;
    private final ActivateProductPort activateProductPort;


    public ProductController(CreateProductPort createProductPort,
                             GetProductPort getProductPort,
                             ListProductsPort listProductsPort,
                             UpdateProductPort updateProductPort,
                             DeactivateProductPort deactivateProductPort,
                             ActivateProductPort activateProductPort) {
        this.createProductPort = createProductPort;
        this.getProductPort = getProductPort;
        this.listProductsPort = listProductsPort;
        this.updateProductPort = updateProductPort;
        this.deactivateProductPort = deactivateProductPort;
        this.activateProductPort = activateProductPort;
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequestDTO dto) {
        Product product = dto.toDomain();
        Product created = createProductPort.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) Boolean active) {
        if (q == null && minStock == null && active == null) {
            return ResponseEntity.ok(listProductsPort.execute());
        }
        return ResponseEntity.ok(listProductsPort.execute(new ProductFilter(q, minStock, active)));
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
                dto.getSalePrice(), dto.getCurrentStock(), dto.getActive()
        );
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        deactivateProductPort.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
public ResponseEntity<Product> updateStatus(@PathVariable Long id,
                                            @RequestBody Map<String, Boolean> body) {
    if (Boolean.TRUE.equals(body.get("active"))) {
        return ResponseEntity.ok(activateProductPort.execute(id));
    } else {
        deactivateProductPort.execute(id);
        return ResponseEntity.noContent().build();
    }
}
    
}