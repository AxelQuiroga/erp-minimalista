package com.example.demo.application.usecase.sale;

import com.example.demo.application.port.out.product.ProductRepositoryPort;
import com.example.demo.application.port.out.sale.SaleRepositoryPort;
import com.example.demo.application.port.out.stock.StockMovementRepositoryPort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.product.Product;
import com.example.demo.domain.model.sale.Sale;
import com.example.demo.domain.model.sale.SaleDetail;
import com.example.demo.domain.model.sale.SaleStatus;
import com.example.demo.domain.model.stock.StockMovement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class CancelSaleUseCaseTest {

    @Mock
    private SaleRepositoryPort saleRepository;

    @Mock
    private ProductRepositoryPort productRepository;

    @Mock
    private StockMovementRepositoryPort stockMovementRepository;

    @InjectMocks
    private CancelSaleUseCase useCase;

    private final Sale completedSale = new Sale(
            1L, 1L,
            List.of(new SaleDetail(null, null, 1L, 2, new BigDecimal("100"))),
            LocalDateTime.now(), SaleStatus.COMPLETED,
            "EFECTIVO", null, null
    );

    private final Product product = new Product(1L, 1L, "Mouse", "MOU-001",
            new BigDecimal("50"), new BigDecimal("100"), 5, true);

    @Test
    void execute_ConDatosValidos_CancelaYRestauraStock() {
        given(saleRepository.findById(1L)).willReturn(Optional.of(completedSale));
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        Sale result = useCase.execute(1L, "Cliente devolvió");

        assertAll(
                () -> assertEquals(SaleStatus.CANCELLED, result.getStatus()),
                () -> assertEquals("Cliente devolvió", result.getCancellationReason())
        );

        // Verificar que restauró stock: 5 + 2 = 7
        verify(productRepository).save(product);
        assertEquals(7, product.getCurrentStock().intValue());

        // Verificar que creó movimiento IN
        verify(stockMovementRepository).save(any(StockMovement.class));
    }

    @Test
    void execute_VentaNoEncontrada_LanzaError() {
        given(saleRepository.findById(99L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> useCase.execute(99L, "Razón")
        );
        verify(productRepository, never()).save(any());
        verify(stockMovementRepository, never()).save(any());
    }

    @Test
    void execute_VentaYaCancelada_LanzaError() {
        Sale cancelledSale = new Sale(
                1L, 1L,
                List.of(new SaleDetail(null, null, 1L, 2, new BigDecimal("100"))),
                LocalDateTime.now(), SaleStatus.CANCELLED,
                "EFECTIVO", null, "Ya cancelada"
        );

        given(saleRepository.findById(1L)).willReturn(Optional.of(cancelledSale));

        assertThrows(BusinessException.class,
                () -> useCase.execute(1L, "Otra cancelación")
        );
        verify(productRepository, never()).save(any());
        verify(stockMovementRepository, never()).save(any());
    }
}
