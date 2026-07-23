package com.example.demo.infrastructure.persistence.sale;

import com.example.demo.application.port.in.sale.SaleFilter;
import com.example.demo.domain.model.sale.Sale;
import com.example.demo.domain.model.sale.SaleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
    "spring.flyway.enabled=false",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Import({SaleRepositoryAdapter.class, SaleMapper.class})
@Sql("/test-data-sales.sql")
class SaleRepositoryAdapterTest {

    @Autowired
    private SaleRepositoryAdapter adapter;

    @Test
    void findById_ConVentaExistente_RetornaVentaConItems() {
        Optional<Sale> result = adapter.findById(1L);

        assertTrue(result.isPresent());
        Sale sale = result.get();

        assertAll(
                () -> assertEquals(1L, sale.getId()),
                () -> assertEquals(1L, sale.getCustomerId()),
                () -> assertEquals(SaleStatus.COMPLETED, sale.getStatus()),
                () -> assertEquals("EFECTIVO", sale.getPaymentMethod()),
                () -> assertEquals(2, sale.getItems().size()),  // JOIN FETCH trae items
                () -> assertEquals(0, new BigDecimal("310").compareTo(sale.getTotalAmount()))
        );
    }

    @Test
    void findById_ConVentaInexistente_RetornaEmpty() {
        Optional<Sale> result = adapter.findById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_RetornaTodasLasVentas() {
        List<Sale> sales = adapter.findAll();

        // 3 ventas en datos de prueba
        assertEquals(3, sales.size());
    }

    @Test
    void findByFilter_SinFiltros_RetornaTodas() {
        SaleFilter filter = new SaleFilter(null, null, null);

        List<Sale> result = adapter.findByFilter(filter);

        assertEquals(3, result.size());
    }

    @Test
    void findByFilter_PorStatusCompleted_RetornaSoloCompletadas() {
        SaleFilter filter = new SaleFilter("COMPLETED", null, null);

        List<Sale> result = adapter.findByFilter(filter);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(s -> s.getStatus() == SaleStatus.COMPLETED));
    }

    @Test
    void findByFilter_PorStatusCancelled_RetornaSoloCanceladas() {
        SaleFilter filter = new SaleFilter("CANCELLED", null, null);

        List<Sale> result = adapter.findByFilter(filter);

        assertEquals(1, result.size());
        assertEquals(SaleStatus.CANCELLED, result.get(0).getStatus());
    }

    @Test
    void findByFilter_PorRangoDeFechas_RetornaVentasEnRango() {
        // Solo ventas de junio 2026
        SaleFilter filter = new SaleFilter(null, LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 30));

        List<Sale> result = adapter.findByFilter(filter);

        assertEquals(2, result.size());
    }

    @Test
    void findByFilter_PorFechaDesde_RetornaVentasDesdeFecha() {
        // Ventas desde julio 2026
        SaleFilter filter = new SaleFilter(null, LocalDate.of(2026, 7, 1), null);

        List<Sale> result = adapter.findByFilter(filter);

        assertEquals(1, result.size());
    }

    @Test
    void findByFilter_PorStatusYFecha_RetornaVentasFiltradas() {
        // Completadas en junio 2026
        SaleFilter filter = new SaleFilter("COMPLETED", LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 30));

        List<Sale> result = adapter.findByFilter(filter);

        assertEquals(1, result.size());
        assertEquals(SaleStatus.COMPLETED, result.get(0).getStatus());
    }

    @Test
    void findTop5Recent_RetornaHasta5Ventas() {
        List<Sale> result = adapter.findTop5Recent();

        assertFalse(result.isEmpty());
        assertTrue(result.size() <= 5);
        // Debe estar ordenado por createdAt DESC
        assertTrue(result.get(0).getCreatedAt().isAfter(result.get(result.size() - 1).getCreatedAt())
                || result.size() == 1);
    }

    @Test
    void count_RetornaCantidadTotal() {
        long count = adapter.count();

        assertEquals(3, count);
    }

    @Test
    void countByCreatedAtAfter_RetornaVentasDesdeFecha() {
        long count = adapter.countByCreatedAtAfter(LocalDateTime.of(2026, 7, 1, 0, 0));

        assertEquals(1, count);
    }

    @Test
    void sumTotalAmountByCreatedAtAfter_RetornaSumaCorrecta() {
        BigDecimal sum = adapter.sumTotalAmountByCreatedAtAfter(LocalDateTime.of(2026, 6, 1, 0, 0));

        // Venta 1 ($310) + Venta 2 ($80) + Venta 3 ($150) = $540
        assertEquals(0, new BigDecimal("540").compareTo(sum));
    }

    @Test
    void save_ConVentaNueva_PersisteYRetornaConId() {
        Sale newSale = new Sale(
                null, 1L,
                List.of(new com.example.demo.domain.model.sale.SaleDetail(
                        null, null, 1L, 1, new BigDecimal("80")
                )),
                LocalDateTime.now(), SaleStatus.COMPLETED,
                "EFECTIVO", "Venta de prueba", null
        );

        Sale saved = adapter.save(newSale);

        assertAll(
                () -> assertNotNull(saved.getId()),
                () -> assertEquals(1L, saved.getCustomerId()),
                () -> assertEquals(1, saved.getItems().size()),
                () -> assertEquals(0, new BigDecimal("80").compareTo(saved.getTotalAmount()))
        );

        // Verificar que se pueda recuperar
        Optional<Sale> retrieved = adapter.findById(saved.getId());
        assertTrue(retrieved.isPresent());
        assertEquals(1, retrieved.get().getItems().size());
    }

    @Test
    void save_ConVentaSinItems_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sale(null, 1L, List.of(),
                        LocalDateTime.now(), SaleStatus.COMPLETED,
                        "EFECTIVO", null, null)
        );
    }
}
