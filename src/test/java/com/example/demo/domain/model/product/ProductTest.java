package com.example.demo.domain.model.product;

import com.example.demo.domain.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    // ============================================================
    // CONSTRUCTOR — Validaciones de creación
    // ============================================================

    @Test
    void constructor_ConDatosValidos_CreaProducto() {
        Product product = new Product(
                1L, 1L, "Teclado Mecánico", "TEC-001",
                new BigDecimal("100"), new BigDecimal("150"),
                10, true
        );

        
        assertAll(
                () -> assertEquals("Teclado Mecánico", product.getName()),
                () -> assertEquals("TEC-001", product.getSku()),
                () -> assertTrue(product.isActive()),
                () -> assertEquals(10, product.getCurrentStock())
        );
    }

    @Test
    void constructor_NombreNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, 1L, null, "SKU-001",
                        BigDecimal.TEN, BigDecimal.TEN, 5, true)
        );
    }

    @Test
    void constructor_NombreBlank_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, 1L, "  ", "SKU-001",
                        BigDecimal.TEN, BigDecimal.TEN, 5, true)
        );
    }

    @Test
    void constructor_SkuNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, 1L, "Producto", null,
                        BigDecimal.TEN, BigDecimal.TEN, 5, true)
        );
    }

    @Test
    void constructor_SkuBlank_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, 1L, "Producto", "  ",
                        BigDecimal.TEN, BigDecimal.TEN, 5, true)
        );
    }

    @Test
    void constructor_CostoCero_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, 1L, "Producto", "SKU-001",
                        BigDecimal.ZERO, BigDecimal.TEN, 5, true)
        );
    }

    @Test
    void constructor_CostoNegativo_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, 1L, "Producto", "SKU-001",
                        new BigDecimal("-10"), BigDecimal.TEN, 5, true)
        );
    }

    @Test
    void constructor_PrecioVentaMenorAlCosto_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, 1L, "Producto", "SKU-001",
                        new BigDecimal("100"), new BigDecimal("50"),
                        5, true)
        );
    }

    @Test
    void constructor_PrecioVentaIgualAlCosto_CreaProducto() {
        // Está bien: puede venderse al mismo precio (margen 0)
        Product product = new Product(null, 1L, "Producto", "SKU-001",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertEquals(0, BigDecimal.TEN.compareTo(product.getSalePrice()));
    }

    @Test
    void constructor_StockNegativo_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, 1L, "Producto", "SKU-001",
                        BigDecimal.TEN, BigDecimal.TEN, -1, true)
        );
    }

    @Test
    void constructor_CategoryIdNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, null, "Producto", "SKU-001",
                        BigDecimal.TEN, BigDecimal.TEN, 5, true)
        );
    }

    // ============================================================
    // removeStock — Control de inventario
    // ============================================================

    @Test
    void removeStock_StockSuficiente_ReduceStock() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 10, true);

        product.removeStock(3);

        assertEquals(7, product.getCurrentStock());
    }

    @Test
    void removeStock_StockInsuficiente_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertThrows(BusinessException.class,
                () -> product.removeStock(10)
        );
    }

    @Test
    void removeStock_CantidadNegativa_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertThrows(IllegalArgumentException.class,
                () -> product.removeStock(-1)
        );
    }

    // ============================================================
    // addStock — Reposición de inventario
    // ============================================================

    @Test
    void addStock_CantidadPositiva_IncrementaStock() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        product.addStock(10);

        assertEquals(15, product.getCurrentStock());
    }

    @Test
    void addStock_CantidadCero_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertThrows(IllegalArgumentException.class,
                () -> product.addStock(0)
        );
    }

    @Test
    void addStock_CantidadNegativa_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertThrows(IllegalArgumentException.class,
                () -> product.addStock(-5)
        );
    }

    // ============================================================
    // Estado (Activo / Inactivo)
    // ============================================================

    @Test
    void deactivate_ProductoActivo_Desactiva() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        product.deactivate();

        assertFalse(product.isActive());
    }

    @Test
    void deactivate_ProductoYaInactivo_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);
        product.deactivate();

        assertThrows(BusinessException.class, product::deactivate);
    }

    @Test
    void activate_ProductoInactivo_Activa() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);
        product.deactivate();

        product.activate();

        assertTrue(product.isActive());
    }

    @Test
    void activate_ProductoYaActivo_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertThrows(BusinessException.class, product::activate);
    }

    // ============================================================
    // updatePrice — Cambio de precios
    // ============================================================

    @Test
    void updatePrice_NuevoCostoCero_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertThrows(IllegalArgumentException.class,
                () -> product.updatePrice(BigDecimal.ZERO, BigDecimal.TEN)
        );
    }

    @Test
    void updatePrice_NuevoPrecioMenorAlCosto_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertThrows(IllegalArgumentException.class,
                () -> product.updatePrice(new BigDecimal("100"), new BigDecimal("50"))
        );
    }

    @Test
    void updatePrice_DatosValidos_Actualiza() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        product.updatePrice(new BigDecimal("150"), new BigDecimal("200"));

        assertEquals(0, new BigDecimal("150").compareTo(product.getCostPrice()));
        assertEquals(0, new BigDecimal("200").compareTo(product.getSalePrice()));
    }

    // ============================================================
    // CONSTRUCTOR — Casos null adicionales
    // ============================================================

    @Test
    void constructor_CostoNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, 1L, "Producto", "SKU-001",
                        null, BigDecimal.TEN, 5, true)
        );
    }

    @Test
    void constructor_PrecioVentaNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, 1L, "Producto", "SKU-001",
                        BigDecimal.TEN, null, 5, true)
        );
    }

    @Test
    void constructor_StockNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(null, 1L, "Producto", "SKU-001",
                        BigDecimal.TEN, BigDecimal.TEN, null, true)
        );
    }

    // ============================================================
    // rename — Cambio de nombre
    // ============================================================

    @Test
    void rename_NombreValido_Actualiza() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        product.rename("Teclado Mecánico");

        assertEquals("Teclado Mecánico", product.getName());
    }

    @Test
    void rename_NombreNull_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertThrows(IllegalArgumentException.class,
                () -> product.rename(null)
        );
    }

    @Test
    void rename_NombreBlank_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertThrows(IllegalArgumentException.class,
                () -> product.rename("  ")
        );
    }

    // ============================================================
    // changeSku — Cambio de SKU
    // ============================================================

    @Test
    void changeSku_SkuValido_Actualiza() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        product.changeSku("TEC-002");

        assertEquals("TEC-002", product.getSku());
    }

    @Test
    void changeSku_SkuNull_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertThrows(IllegalArgumentException.class,
                () -> product.changeSku(null)
        );
    }

    @Test
    void changeSku_SkuBlank_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertThrows(IllegalArgumentException.class,
                () -> product.changeSku("  ")
        );
    }

    // ============================================================
    // setSalePrice — Cambio de precio de venta
    // ============================================================

    @Test
    void setSalePrice_PrecioValido_Actualiza() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        product.setSalePrice(new BigDecimal("200"));

        assertEquals(0, new BigDecimal("200").compareTo(product.getSalePrice()));
    }

    @Test
    void setSalePrice_PrecioMenorAlCosto_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                new BigDecimal("100"), new BigDecimal("150"), 5, true);

        assertThrows(IllegalArgumentException.class,
                () -> product.setSalePrice(new BigDecimal("50"))
        );
    }

    // ============================================================
    // updateStock — Actualización directa de stock
    // ============================================================

    @Test
    void updateStock_StockValido_Actualiza() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        product.updateStock(20);

        assertEquals(20, product.getCurrentStock().intValue());
    }

    @Test
    void updateStock_StockNegativo_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertThrows(IllegalArgumentException.class,
                () -> product.updateStock(-1)
        );
    }

    @Test
    void updateStock_StockNull_LanzaError() {
        Product product = new Product(null, 1L, "X", "X",
                BigDecimal.TEN, BigDecimal.TEN, 5, true);

        assertThrows(IllegalArgumentException.class,
                () -> product.updateStock(null)
        );
    }
}