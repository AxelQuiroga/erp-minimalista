package com.example.demo.infrastructure.web;

import com.example.demo.application.port.in.CreateSalePort;
import com.example.demo.application.port.in.GetSalePort;
import com.example.demo.application.port.in.ListSalesPort;
import com.example.demo.domain.model.Sale;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final CreateSalePort createSalePort;
    private final GetSalePort getSalePort;
    private final ListSalesPort listSalesPort;

    public SaleController(CreateSalePort createSalePort,
                           GetSalePort getSalePort,
                           ListSalesPort listSalesPort) {
        this.createSalePort = createSalePort;
        this.getSalePort = getSalePort;
        this.listSalesPort = listSalesPort;
    }

    @PostMapping
    public ResponseEntity<Sale> create(@Valid @RequestBody CreateSaleRequest request) {
        Sale sale = createSalePort.execute(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(sale);
    }

    @GetMapping
    public ResponseEntity<List<Sale>> getAll() {
        return ResponseEntity.ok(listSalesPort.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getSalePort.execute(id));
    }
}
