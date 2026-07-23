package com.example.demo.domain.model.sale;

import com.example.demo.domain.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

    private SaleDetail detail(BigDecimal price, int quantity) {
        return new SaleDetail(null, null, 1L, quantity, price);
    }

    // ============================================================
    // CONSTRUCTOR — Validaciones de creación
    // ============================================================

    @Test
    void constructor_ConDatosValidos_CreaVenta() {
        Sale sale = new Sale(
                null, 1L, List.of(detail(BigDecimal.TEN, 2)),
                LocalDateTime.now(), SaleStatus.COMPLETED,
                "EFECTIVO", "Sin notas", null
        );

        assertAll(
                () -> assertEquals(1L, sale.getCustomerId()),
                () -> assertEquals(SaleStatus.COMPLETED, sale.getStatus()),
                () -> assertEquals("EFECTIVO", sale.getPaymentMethod()),
                () -> assertNotNull(sale.getCreatedAt()),
                () -> assertNotNull(sale.getItems()),
                () -> assertEquals(1, sale.getItems().size())
        );
    }

    @Test
    void constructor_CustomerIdNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sale(null, null, List.of(detail(BigDecimal.TEN, 1)),
                        LocalDateTime.now(), SaleStatus.COMPLETED,
                        "EFECTIVO", null, null)
        );
    }

    @Test
    void constructor_ItemsNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sale(null, 1L, null,
                        LocalDateTime.now(), SaleStatus.COMPLETED,
                        "EFECTIVO", null, null)
        );
    }

    @Test
    void constructor_ItemsVacio_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sale(null, 1L, List.of(),
                        LocalDateTime.now(), SaleStatus.COMPLETED,
                        "EFECTIVO", null, null)
        );
    }

    @Test
    void constructor_StatusNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sale(null, 1L, List.of(detail(BigDecimal.TEN, 1)),
                        LocalDateTime.now(), null,
                        "EFECTIVO", null, null)
        );
    }

    @Test
    void constructor_CalculaTotalAmount_SumaSubtotales() {
        SaleDetail item1 = detail(new BigDecimal("100"), 2);  // subtotal = 200
        SaleDetail item2 = detail(new BigDecimal("50"), 3);   // subtotal = 150

        Sale sale = new Sale(null, 1L, List.of(item1, item2),
                LocalDateTime.now(), SaleStatus.COMPLETED,
                "EFECTIVO", null, null);

        assertEquals(0, new BigDecimal("350").compareTo(sale.getTotalAmount()));
    }

    // ============================================================
    // cancel — Cancelación de ventas
    // ============================================================

    @Test
    void cancel_EstadoCompleted_Cancela() {
        Sale sale = new Sale(null, 1L, List.of(detail(BigDecimal.TEN, 1)),
                LocalDateTime.now(), SaleStatus.COMPLETED,
                "EFECTIVO", null, null);

        sale.cancel("Cliente devolvió el producto");

        assertAll(
                () -> assertEquals(SaleStatus.CANCELLED, sale.getStatus()),
                () -> assertEquals("Cliente devolvió el producto", sale.getCancellationReason())
        );
    }

    @Test
    void cancel_EstadoCompletedSinRazon_Cancela() {
        Sale sale = new Sale(null, 1L, List.of(detail(BigDecimal.TEN, 1)),
                LocalDateTime.now(), SaleStatus.COMPLETED,
                "EFECTIVO", null, null);

        sale.cancel(null);

        assertEquals(SaleStatus.CANCELLED, sale.getStatus());
    }

    @Test
    void cancel_VentaYaCancelada_LanzaError() {
        Sale sale = new Sale(null, 1L, List.of(detail(BigDecimal.TEN, 1)),
                LocalDateTime.now(), SaleStatus.CANCELLED,
                "EFECTIVO", null, "Ya cancelada");

        assertThrows(BusinessException.class,
                () -> sale.cancel("Otra razón")
        );
    }
}
