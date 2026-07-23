package com.example.demo.infrastructure.persistence.customer;

import com.example.demo.application.port.in.customer.CustomerFilter;
import com.example.demo.domain.model.customer.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
    "spring.flyway.enabled=false",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Import({CustomerRepositoryAdapter.class, CustomerMapper.class})
@Sql("/test-data-customers.sql")
class CustomerRepositoryAdapterTest {

    @Autowired
    private CustomerRepositoryAdapter adapter;

    @Test
    void findById_ConClienteExistente_RetornaCliente() {
        Optional<Customer> result = adapter.findById(1L);

        assertTrue(result.isPresent());
        assertAll(
                () -> assertEquals(1L, result.get().getId()),
                () -> assertEquals("Juan Pérez", result.get().getName()),
                () -> assertEquals("juan@example.com", result.get().getEmail()),
                () -> assertTrue(result.get().isActive())
        );
    }

    @Test
    void findById_ConClienteInexistente_RetornaEmpty() {
        Optional<Customer> result = adapter.findById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void existsByEmail_ConEmailExistente_RetornaTrue() {
        assertTrue(adapter.existsByEmail("juan@example.com"));
    }

    @Test
    void existsByEmail_ConEmailInexistente_RetornaFalse() {
        assertFalse(adapter.existsByEmail("noexiste@example.com"));
    }

    @Test
    void findAll_RetornaTodosLosClientes() {
        List<Customer> customers = adapter.findAll();

        assertEquals(4, customers.size());
    }

    @Test
    void findByFilter_SinFiltros_RetornaTodos() {
        CustomerFilter filter = new CustomerFilter(null, null);

        List<Customer> result = adapter.findByFilter(filter);

        assertEquals(4, result.size());
    }

    @Test
    void findByFilter_PorNombre_RetornaCoincidencias() {
        CustomerFilter filter = new CustomerFilter("Juan", null);

        List<Customer> result = adapter.findByFilter(filter);

        assertEquals(1, result.size());
        assertEquals("Juan Pérez", result.get(0).getName());
    }

    @Test
    void findByFilter_PorEmail_RetornaCoincidencias() {
        CustomerFilter filter = new CustomerFilter("maria", null);

        List<Customer> result = adapter.findByFilter(filter);

        assertEquals(1, result.size());
        assertEquals("maria@example.com", result.get(0).getEmail());
    }

    @Test
    void findByFilter_PorActivos_RetornaSoloActivos() {
        CustomerFilter filter = new CustomerFilter(null, true);

        List<Customer> result = adapter.findByFilter(filter);

        assertEquals(3, result.size());
        assertTrue(result.stream().allMatch(Customer::isActive));
    }

    @Test
    void findByFilter_PorInactivos_RetornaSoloInactivos() {
        CustomerFilter filter = new CustomerFilter(null, false);

        List<Customer> result = adapter.findByFilter(filter);

        assertEquals(1, result.size());
        assertFalse(result.get(0).isActive());
    }

    @Test
    void findByFilter_Combinado_RetornaCoincidencias() {
        // Activos que contengan "a" en nombre o email
        CustomerFilter filter = new CustomerFilter("a", true);

        List<Customer> result = adapter.findByFilter(filter);

        assertTrue(result.size() > 0);
        assertTrue(result.stream().allMatch(Customer::isActive));
    }

    @Test
    void findByFilter_SinResultados_RetornaListaVacia() {
        CustomerFilter filter = new CustomerFilter("zzzzzz", null);

        List<Customer> result = adapter.findByFilter(filter);

        assertTrue(result.isEmpty());
    }

    @Test
    void count_RetornaCantidadTotal() {
        assertEquals(4, adapter.count());
    }

    @Test
    void save_ConClienteNuevo_PersisteYRetornaConId() {
        Customer newCustomer = new Customer(
                null, "Nuevo Cliente", "nuevo@example.com",
                "999999999", "Dirección nueva",
                true, LocalDateTime.now()
        );

        Customer saved = adapter.save(newCustomer);

        assertAll(
                () -> assertNotNull(saved.getId()),
                () -> assertEquals("Nuevo Cliente", saved.getName()),
                () -> assertEquals("nuevo@example.com", saved.getEmail())
        );

        // Verificar persistencia real
        assertTrue(adapter.existsByEmail("nuevo@example.com"));
        assertEquals(5, adapter.count());
    }

    @Test
    void save_ConEmailDuplicado_LanzaError() {
        Customer duplicate = new Customer(
                null, "Otro Juan", "juan@example.com",
                null, null, true, LocalDateTime.now()
        );

        assertThrows(Exception.class,
                () -> adapter.save(duplicate)
        );
    }
}
