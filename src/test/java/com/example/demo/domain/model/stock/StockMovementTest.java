package com.example.demo.domain.model.stock;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockMovementTest {

    // ============================================================
    // inbound — Ingreso de stock
    // ============================================================

    @Test
    void inbound_ConDatosValidos_CreaMovimiento() {
        StockMovement movement = StockMovement.inbound(1L, 10, "Compra a proveedor");

        assertAll(
                () -> assertEquals(1L, movement.getProductId()),
                () -> assertEquals(10, movement.getQuantity()),
                () -> assertEquals(MovementType.IN, movement.getType()),
                () -> assertEquals("Compra a proveedor", movement.getReason()),
                () -> assertNull(movement.getId()),
                () -> assertNull(movement.getCreatedAt())
        );
    }

    @Test
    void inbound_QuantityCero_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> StockMovement.inbound(1L, 0, "Test")
        );
    }

    @Test
    void inbound_QuantityNegativo_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> StockMovement.inbound(1L, -5, "Test")
        );
    }

    @Test
    void inbound_ProductIdNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> StockMovement.inbound(null, 10, "Test")
        );
    }

    // ============================================================
    // outbound — Egreso de stock
    // ============================================================

    @Test
    void outbound_ConDatosValidos_CreaMovimiento() {
        StockMovement movement = StockMovement.outbound(1L, 5, "Venta");

        assertAll(
                () -> assertEquals(1L, movement.getProductId()),
                () -> assertEquals(-5, movement.getQuantity()),  // firmado negativo
                () -> assertEquals(MovementType.OUT, movement.getType()),
                () -> assertEquals("Venta", movement.getReason())
        );
    }

    @Test
    void outbound_QuantityCero_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> StockMovement.outbound(1L, 0, "Test")
        );
    }

    @Test
    void outbound_QuantityNegativo_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> StockMovement.outbound(1L, -3, "Test")
        );
    }

    @Test
    void outbound_ProductIdNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> StockMovement.outbound(null, 5, "Test")
        );
    }

    // ============================================================
    // adjustment — Ajuste de stock
    // ============================================================

    @Test
    void adjustment_ConCantidadPositiva_CreaMovimiento() {
        StockMovement movement = StockMovement.adjustment(1L, 20, "Ajuste por inventario");

        assertAll(
                () -> assertEquals(1L, movement.getProductId()),
                () -> assertEquals(20, movement.getQuantity()),
                () -> assertEquals(MovementType.ADJUSTMENT, movement.getType())
        );
    }

    @Test
    void adjustment_ConCantidadNegativa_CreaMovimiento() {
        StockMovement movement = StockMovement.adjustment(1L, -10, "Ajuste por merma");

        assertEquals(-10, movement.getQuantity());
    }

    @Test
    void adjustment_QuantityCero_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> StockMovement.adjustment(1L, 0, "Test")
        );
    }

    @Test
    void adjustment_ProductIdNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> StockMovement.adjustment(null, 5, "Test")
        );
    }

    // ============================================================
    // reconstitute — Reconstrucción desde BD (sin validación)
    // ============================================================

    @Test
    void reconstitute_CreaObjetoSinValidacion() {
        StockMovement movement = StockMovement.reconstitute(
                99L, 1L, -5, MovementType.OUT, null, "Venta"
        );

        assertAll(
                () -> assertEquals(99L, movement.getId()),
                () -> assertEquals(-5, movement.getQuantity()),
                () -> assertEquals(MovementType.OUT, movement.getType()),
                () -> assertNull(movement.getCreatedAt())
        );
    }

    // ============================================================
    // getAbsoluteQuantity — Valor absoluto
    // ============================================================

    @Test
    void getAbsoluteQuantity_QuantityPositivo_DevuelveMismoValor() {
        StockMovement movement = StockMovement.inbound(1L, 15, "Test");

        assertEquals(15, movement.getAbsoluteQuantity());
    }

    @Test
    void getAbsoluteQuantity_QuantityNegativo_DevuelveValorAbsoluto() {
        StockMovement movement = StockMovement.outbound(1L, 8, "Test");

        assertEquals(8, movement.getAbsoluteQuantity());
    }
}
