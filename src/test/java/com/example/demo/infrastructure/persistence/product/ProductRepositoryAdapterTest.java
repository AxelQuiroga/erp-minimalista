package com.example.demo.infrastructure.persistence.product;

import com.example.demo.application.port.in.product.ProductFilter;
import com.example.demo.domain.model.product.Product;
import com.example.demo.infrastructure.persistence.category.CategoryMapper;
import com.example.demo.infrastructure.persistence.category.CategoryRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
    "spring.flyway.enabled=false",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Import({ProductRepositoryAdapter.class, ProductMapper.class, CategoryRepositoryAdapter.class, CategoryMapper.class})
@Sql("/test-data-products.sql")
class ProductRepositoryAdapterTest {

    @Autowired
    private ProductRepositoryAdapter adapter;

    @Test
    void findById_ConProductoExistente_RetornaProducto() {
        Optional<Product> result = adapter.findById(1L);

        assertTrue(result.isPresent());
        assertAll(
                () -> assertEquals(1L, result.get().getId()),
                () -> assertEquals("Mouse", result.get().getName()),
                () -> assertEquals("MOU-001", result.get().getSku()),
                () -> assertTrue(result.get().isActive())
        );
    }

    @Test
    void findById_ConProductoInexistente_RetornaEmpty() {
        Optional<Product> result = adapter.findById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void existsBySku_ConSkuExistente_RetornaTrue() {
        assertTrue(adapter.existsBySku("MOU-001"));
    }

    @Test
    void existsBySku_ConSkuInexistente_RetornaFalse() {
        assertFalse(adapter.existsBySku("SKU-INEXISTENTE"));
    }

    @Test
    void findAll_RetornaTodosLosProductos() {
        List<Product> products = adapter.findAll();

        assertEquals(5, products.size());
    }

    @Test
    void findByFilter_SinFiltros_RetornaTodos() {
        ProductFilter filter = new ProductFilter(null, null, null);

        List<Product> result = adapter.findByFilter(filter);

        assertEquals(5, result.size());
    }

    @Test
    void findByFilter_PorNombre_RetornaCoincidencias() {
        ProductFilter filter = new ProductFilter("mouse", null, null);

        List<Product> result = adapter.findByFilter(filter);

        assertEquals(2, result.size()); // "Mouse" y "Mouse Inalámbrico"
        assertTrue(result.stream().allMatch(p -> p.getName().toLowerCase().contains("mouse")));
    }

    @Test
    void findByFilter_PorSku_RetornaCoincidencias() {
        ProductFilter filter = new ProductFilter("TEC", null, null);

        List<Product> result = adapter.findByFilter(filter);

        assertEquals(2, result.size()); // "TEC-001" y "TEC-002"
    }

    @Test
    void findByFilter_PorActivos_RetornaSoloActivos() {
        ProductFilter filter = new ProductFilter(null, null, true);

        List<Product> result = adapter.findByFilter(filter);

        assertEquals(4, result.size());
        assertTrue(result.stream().allMatch(Product::isActive));
    }

    @Test
    void findByFilter_PorInactivos_RetornaSoloInactivos() {
        ProductFilter filter = new ProductFilter(null, null, false);

        List<Product> result = adapter.findByFilter(filter);

        assertEquals(1, result.size());
        assertFalse(result.get(0).isActive());
    }

    @Test
    void findByFilter_PorStockMinimo_RetornaProductosConStockSuficiente() {
        // minStock = 5: productos con currentStock >= 5
        ProductFilter filter = new ProductFilter(null, 5, null);

        List<Product> result = adapter.findByFilter(filter);

        assertEquals(3, result.size()); // Mouse (10), Teclado (5), Teclado Mecánico (8)
    }

    @Test
    void findByFilter_PorStockCero_RetornaIncluyeSinStock() {
        // minStock = 0: todos porque >= 0
        ProductFilter filter = new ProductFilter(null, 0, null);

        List<Product> result = adapter.findByFilter(filter);

        assertEquals(5, result.size());
    }

    @Test
    void findByFilter_Combinado_RetornaCoincidencias() {
        // Activos que contengan "Mouse" con stock >= 5
        ProductFilter filter = new ProductFilter("Mouse", 5, true);

        List<Product> result = adapter.findByFilter(filter);

        assertEquals(1, result.size()); // Solo "Mouse" (stock 10, activo)
        assertEquals("MOU-001", result.get(0).getSku());
    }

    @Test
    void hasActiveProductsByCategory_ConProductosActivos_RetornaTrue() {
        assertTrue(adapter.hasActiveProductsByCategory(1L));
    }

    @Test
    void hasActiveProductsByCategory_SinProductosActivos_RetornaFalse() {
        assertFalse(adapter.hasActiveProductsByCategory(99L));
    }

    @Test
    void countByCurrentStockLessThan_RetornaCantidadCorrecta() {
        // Productos con stock < 5: Monitor Inactivo (3), Mouse Inalámbrico (0)
        long count = adapter.countByCurrentStockLessThan(5);

        assertEquals(2, count);
    }

    @Test
    void count_RetornaCantidadTotal() {
        assertEquals(5, adapter.count());
    }

    @Test
    void save_ConProductoNuevo_PersisteYRetornaConId() {
        Product newProduct = new Product(
                null, 1L, "Parlantes", "PAR-001",
                new BigDecimal("30"), new BigDecimal("60"),
                20, true
        );

        Product saved = adapter.save(newProduct);

        assertAll(
                () -> assertNotNull(saved.getId()),
                () -> assertEquals("Parlantes", saved.getName()),
                () -> assertEquals("PAR-001", saved.getSku())
        );

        // Verificar persistencia real
        assertTrue(adapter.existsBySku("PAR-001"));
        assertEquals(6, adapter.count());
    }
}
