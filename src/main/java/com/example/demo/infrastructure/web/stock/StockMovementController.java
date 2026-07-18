package com.example.demo.infrastructure.web.stock;

import com.example.demo.application.port.in.stock.ListMovementsByProductPort;
import com.example.demo.application.port.in.stock.RegisterMovementPort;
import com.example.demo.domain.model.stock.StockMovement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock-movements")
public class StockMovementController {

    private final RegisterMovementPort registerMovementPort;
    private final ListMovementsByProductPort listMovementsByProductPort;

    public StockMovementController(RegisterMovementPort registerMovementPort,
                                    ListMovementsByProductPort listMovementsByProductPort) {
        this.registerMovementPort = registerMovementPort;
        this.listMovementsByProductPort = listMovementsByProductPort;
    }

    @PostMapping
    public ResponseEntity<StockMovement> register(@Valid @RequestBody RegisterMovementRequest request) {
        StockMovement movement = registerMovementPort.execute(
                request.productId(), request.quantity(), request.type(), request.reason()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(movement);
    }

    @GetMapping
    public ResponseEntity<Page<StockMovement>> listByProduct(@RequestParam Long productId, Pageable pageable) {
        return ResponseEntity.ok(listMovementsByProductPort.execute(productId, pageable));
    }
}
