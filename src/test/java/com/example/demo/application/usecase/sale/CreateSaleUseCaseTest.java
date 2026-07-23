package com.example.demo.application.usecase.sale;

import com.example.demo.application.port.in.sale.CreateSalePort;
import com.example.demo.application.port.out.customer.CustomerRepositoryPort;
import com.example.demo.application.port.out.product.ProductRepositoryPort;
import com.example.demo.application.port.out.sale.SaleRepositoryPort;
import com.example.demo.application.port.out.stock.StockMovementRepositoryPort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.customer.Customer;
import com.example.demo.domain.model.product.Product;
import com.example.demo.domain.model.sale.Sale;
import com.example.demo.domain.model.stock.StockMovement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSaleUseCaseTest {

    @Mock
    private CustomerRepositoryPort customerRepository;

    @Mock
    private ProductRepositoryPort productRepository;

    @Mock
    private SaleRepositoryPort saleRepository;

    @Mock
    private StockMovementRepositoryPort stockMovementRepository;

    @InjectMocks
    private CreateSaleUseCase useCase;

    @Captor
    private ArgumentCaptor<Sale> saleCaptor;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    private final Customer activeCustomer = new Customer(1L, "Juan", "juan@example.com",
            "123", "Calle", true, LocalDateTime.now());

    private final Product mouse = new Product(1L, 1L, "Mouse", "MOU-001",
            new BigDecimal("50"), new BigDecimal("80"), 10, true);

    private final Product teclado = new Product(2L, 1L, "Teclado", "TEC-001",
            new BigDecimal("100"), new BigDecimal("150"), 5, true);

    @Test
    void execute_ConDatosValidos_CreaVentaYDescuentaStock() {
        CreateSalePort.CreateSaleCommand command = new CreateSalePort.CreateSaleCommand(
                1L,
                List.of(
                        new CreateSalePort.SaleItemCommand(1L, 2, new BigDecimal("80")),
                        new CreateSalePort.SaleItemCommand(2L, 1, new BigDecimal("150"))
                ),
                "EFECTIVO", "Nota de prueba"
        );

        given(customerRepository.findById(1L)).willReturn(Optional.of(activeCustomer));
        given(productRepository.findById(1L)).willReturn(Optional.of(mouse));
        given(productRepository.findById(2L)).willReturn(Optional.of(teclado));
        given(saleRepository.save(any(Sale.class))).willAnswer(invocation -> {
            Sale s = invocation.getArgument(0);
            // Simular que la venta se guarda con ID asignado
            return new Sale(100L, s.getCustomerId(), s.getItems(), s.getCreatedAt(),
                    s.getStatus(), s.getPaymentMethod(), s.getNotes(), null);
        });

        Sale result = useCase.execute(command);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.getItems().size()),
                () -> assertEquals(1L, result.getCustomerId())
        );

        // Verificar que actualizó stock del mouse (10 - 2 = 8)
        verify(productRepository, times(2)).save(productCaptor.capture());
        List<Product> savedProducts = productCaptor.getAllValues();
        assertEquals(8, savedProducts.get(0).getCurrentStock().intValue()); // mouse
        assertEquals(4, savedProducts.get(1).getCurrentStock().intValue()); // teclado

        // Verificar que creó movimientos de stock
        verify(stockMovementRepository, times(2)).save(any(StockMovement.class));
    }

    @Test
    void execute_ClienteNoEncontrado_LanzaError() {
        CreateSalePort.CreateSaleCommand command = new CreateSalePort.CreateSaleCommand(
                99L, List.of(new CreateSalePort.SaleItemCommand(1L, 1, BigDecimal.TEN)),
                "EFECTIVO", null
        );

        given(customerRepository.findById(99L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.execute(command));
        verify(saleRepository, never()).save(any());
        verify(stockMovementRepository, never()).save(any());
    }

    @Test
    void execute_ClienteInactivo_LanzaError() {
        Customer inactiveCustomer = new Customer(1L, "Juan", "juan@example.com",
                "123", "Calle", false, LocalDateTime.now());

        CreateSalePort.CreateSaleCommand command = new CreateSalePort.CreateSaleCommand(
                1L, List.of(new CreateSalePort.SaleItemCommand(1L, 1, BigDecimal.TEN)),
                "EFECTIVO", null
        );

        given(customerRepository.findById(1L)).willReturn(Optional.of(inactiveCustomer));

        BusinessException ex = assertThrows(BusinessException.class, () -> useCase.execute(command));
        assertTrue(ex.getMessage().contains("desactivado"));
        verify(saleRepository, never()).save(any());
    }

    @Test
    void execute_ProductoNoEncontrado_LanzaError() {
        CreateSalePort.CreateSaleCommand command = new CreateSalePort.CreateSaleCommand(
                1L, List.of(new CreateSalePort.SaleItemCommand(99L, 1, BigDecimal.TEN)),
                "EFECTIVO", null
        );

        given(customerRepository.findById(1L)).willReturn(Optional.of(activeCustomer));
        given(productRepository.findById(99L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.execute(command));
        verify(saleRepository, never()).save(any());
    }

    @Test
    void execute_ProductoInactivo_LanzaError() {
        Product inactiveProduct = new Product(1L, 1L, "Inactivo", "XXX",
                BigDecimal.TEN, BigDecimal.TEN, 5, false);

        CreateSalePort.CreateSaleCommand command = new CreateSalePort.CreateSaleCommand(
                1L, List.of(new CreateSalePort.SaleItemCommand(1L, 1, BigDecimal.TEN)),
                "EFECTIVO", null
        );

        given(customerRepository.findById(1L)).willReturn(Optional.of(activeCustomer));
        given(productRepository.findById(1L)).willReturn(Optional.of(inactiveProduct));

        BusinessException ex = assertThrows(BusinessException.class, () -> useCase.execute(command));
        assertTrue(ex.getMessage().contains("desactivado"));
        verify(saleRepository, never()).save(any());
    }

    @Test
    void execute_StockInsuficiente_LanzaError() {
        Product lowStockProduct = new Product(1L, 1L, "Mouse", "MOU-001",
                new BigDecimal("50"), new BigDecimal("80"), 2, true);

        CreateSalePort.CreateSaleCommand command = new CreateSalePort.CreateSaleCommand(
                1L, List.of(new CreateSalePort.SaleItemCommand(1L, 5, new BigDecimal("80"))),
                "EFECTIVO", null
        );

        given(customerRepository.findById(1L)).willReturn(Optional.of(activeCustomer));
        given(productRepository.findById(1L)).willReturn(Optional.of(lowStockProduct));

        assertThrows(BusinessException.class, () -> useCase.execute(command));
        verify(saleRepository, never()).save(any());
        verify(stockMovementRepository, never()).save(any());
    }
}
