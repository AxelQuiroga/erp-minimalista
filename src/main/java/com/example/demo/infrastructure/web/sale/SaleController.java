package com.example.demo.infrastructure.web.sale;

import com.example.demo.application.port.in.sale.CancelSalePort;
import com.example.demo.application.port.in.sale.CreateSalePort;
import com.example.demo.application.port.in.sale.GetSalePort;
import com.example.demo.application.port.in.sale.ListSalesPort;
import com.example.demo.application.port.in.sale.SaleFilter;
import com.example.demo.domain.model.sale.Sale;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final CreateSalePort createSalePort;
    private final GetSalePort getSalePort;
    private final ListSalesPort listSalesPort;
    private final CancelSalePort cancelSalePort;

    public SaleController(CreateSalePort createSalePort,
                           GetSalePort getSalePort,
                           ListSalesPort listSalesPort,
                           CancelSalePort cancelSalePort) {
        this.createSalePort = createSalePort;
        this.getSalePort = getSalePort;
        this.listSalesPort = listSalesPort;
        this.cancelSalePort = cancelSalePort;
    }

    @PostMapping
    public ResponseEntity<Sale> create(@Valid @RequestBody CreateSaleRequest request) {
        Sale sale = createSalePort.execute(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(sale);
    }

    @GetMapping
    public ResponseEntity<List<Sale>> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {
        if (status == null && from == null && to == null) {
            return ResponseEntity.ok(listSalesPort.execute());
        }
        return ResponseEntity.ok(listSalesPort.execute(new SaleFilter(status, from, to)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getSalePort.execute(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Sale> cancel(@PathVariable Long id,
                                        @RequestBody(required = false) CancelSaleRequest request) {
        String reason = request != null ? request.reason() : null;
        Sale sale = cancelSalePort.execute(id, reason);
        return ResponseEntity.ok(sale);
    }
}
