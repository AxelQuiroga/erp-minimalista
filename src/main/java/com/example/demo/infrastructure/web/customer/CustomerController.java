package com.example.demo.infrastructure.web.customer;

import com.example.demo.application.port.in.customer.ActivateCustomerPort;
import com.example.demo.application.port.in.customer.CreateCustomerPort;
import com.example.demo.application.port.in.customer.DeactivateCustomerPort;
import com.example.demo.application.port.in.customer.GetCustomerPort;
import com.example.demo.application.port.in.customer.ListCustomersPort;
import com.example.demo.application.port.in.customer.UpdateCustomerPort;
import com.example.demo.domain.model.customer.Customer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CreateCustomerPort createCustomerPort;
    private final GetCustomerPort getCustomerPort;
    private final ListCustomersPort listCustomersPort;
    private final UpdateCustomerPort updateCustomerPort;
    private final DeactivateCustomerPort deactivateCustomerPort;
    private final ActivateCustomerPort activateCustomerPort;

    public CustomerController(CreateCustomerPort createCustomerPort,
                               GetCustomerPort getCustomerPort,
                               ListCustomersPort listCustomersPort,
                               UpdateCustomerPort updateCustomerPort,
                               DeactivateCustomerPort deactivateCustomerPort,
                               ActivateCustomerPort activateCustomerPort) {
        this.createCustomerPort = createCustomerPort;
        this.getCustomerPort = getCustomerPort;
        this.listCustomersPort = listCustomersPort;
        this.updateCustomerPort = updateCustomerPort;
        this.deactivateCustomerPort = deactivateCustomerPort;
        this.activateCustomerPort = activateCustomerPort;
    }

    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody CustomerRequestDTO dto) {
        Customer saved = createCustomerPort.execute(dto.name(), dto.email(), dto.phone(), dto.address());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(listCustomersPort.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getCustomerPort.execute(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id,
                                           @Valid @RequestBody CustomerUpdateDTO dto) {
        Customer updated = updateCustomerPort.execute(id, dto.name(), dto.email(), dto.phone(), dto.address(), dto.active());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        deactivateCustomerPort.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
public ResponseEntity<Customer> updateStatus(@PathVariable Long id,
                                             @RequestBody Map<String, Boolean> body) {
    if (Boolean.TRUE.equals(body.get("active"))) {
        return ResponseEntity.ok(activateCustomerPort.execute(id));
    } else {
        deactivateCustomerPort.execute(id);
        return ResponseEntity.noContent().build();
    }
}
}
