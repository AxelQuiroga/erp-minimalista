package com.example.demo.domain.model.sale;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SaleDetailTest {

    // ============================================================
    // CONSTRUCTOR — Validaciones de creación
    // ============================================================

    @Test
    void constructor_ConDatosValidos_CreaDetalle() {
        SaleDetail detail = new SaleDetail(null, null, 1L, 2, new BigDecimal("150"));

        assertAll(
                () -> assertEquals(1L, detail.getProductId()),
                () -> assertEquals(2, detail.getQuantity()),
                () -> assertEquals(0, new BigDecimal("150").compareTo(detail.getUnitPrice()))
        );
    }

    @Test
    void constructor_ProductIdNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new SaleDetail(null, null, null, 1, BigDecimal.TEN)
        );
    }

    @Test
    void constructor_QuantityNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new SaleDetail(null, null, 1L, null, BigDecimal.TEN)
        );
    }

    @Test
    void constructor_QuantityCero_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new SaleDetail(null, null, 1L, 0, BigDecimal.TEN)
        );
    }

    @Test
    void constructor_QuantityNegativo_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new SaleDetail(null, null, 1L, -1, BigDecimal.TEN)
        );
    }

    @Test
    void constructor_UnitPriceNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new SaleDetail(null, null, 1L, 1, null)
        );
    }

    @Test
    void constructor_UnitPriceCero_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new SaleDetail(null, null, 1L, 1, BigDecimal.ZERO)
        );
    }

    @Test
    void constructor_UnitPriceNegativo_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new SaleDetail(null, null, 1L, 1, new BigDecimal("-10"))
        );
    }

    // ============================================================
    // getSubtotal — Cálculo de subtotal
    // ============================================================

    @Test
    void getSubtotal_CalculaCorrectamente() {
        SaleDetail detail = new SaleDetail(null, null, 1L, 3, new BigDecimal("100"));

        assertEquals(0, new BigDecimal("300").compareTo(detail.getSubtotal()));
    }

    @Test
    void getSubtotal_ConCantidadUno_EsIgualAlPrecio() {
        SaleDetail detail = new SaleDetail(null, null, 1L, 1, new BigDecimal("250"));

        assertEquals(0, new BigDecimal("250").compareTo(detail.getSubtotal()));
    }
}
