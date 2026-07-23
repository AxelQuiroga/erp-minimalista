package com.example.demo.domain.model.customer;

import com.example.demo.domain.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    // ============================================================
    // CONSTRUCTOR — Validaciones de creación
    // ============================================================

    @Test
    void constructor_ConDatosValidos_CreaCliente() {
        Customer customer = new Customer(
                null, "Juan Pérez", "juan@example.com",
                "123456789", "Calle Falsa 123",
                true, LocalDateTime.now()
        );

        assertAll(
                () -> assertEquals("Juan Pérez", customer.getName()),
                () -> assertEquals("juan@example.com", customer.getEmail()),
                () -> assertEquals("123456789", customer.getPhone()),
                () -> assertEquals("Calle Falsa 123", customer.getAddress()),
                () -> assertTrue(customer.isActive()),
                () -> assertNotNull(customer.getCreatedAt())
        );
    }

    @Test
    void constructor_NombreNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Customer(null, null, "juan@example.com",
                        "123456789", "Calle Falsa 123",
                        true, LocalDateTime.now())
        );
    }

    @Test
    void constructor_NombreBlank_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Customer(null, "  ", "juan@example.com",
                        "123456789", "Calle Falsa 123",
                        true, LocalDateTime.now())
        );
    }

    @Test
    void constructor_EmailNull_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Customer(null, "Juan Pérez", null,
                        "123456789", "Calle Falsa 123",
                        true, LocalDateTime.now())
        );
    }

    @Test
    void constructor_EmailBlank_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Customer(null, "Juan Pérez", "  ",
                        "123456789", "Calle Falsa 123",
                        true, LocalDateTime.now())
        );
    }

    @Test
    void constructor_EmailSinArroba_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Customer(null, "Juan Pérez", "juanexample.com",
                        "123456789", "Calle Falsa 123",
                        true, LocalDateTime.now())
        );
    }

    @Test
    void constructor_EmailSinPunto_LanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Customer(null, "Juan Pérez", "juan@example",
                        "123456789", "Calle Falsa 123",
                        true, LocalDateTime.now())
        );
    }

    // ============================================================
    // rename — Cambio de nombre
    // ============================================================

    @Test
    void rename_NombreValido_Actualiza() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());

        customer.rename("Juan Pérez");

        assertEquals("Juan Pérez", customer.getName());
    }

    @Test
    void rename_NombreNull_LanzaError() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());

        assertThrows(IllegalArgumentException.class,
                () -> customer.rename(null)
        );
    }

    @Test
    void rename_NombreBlank_LanzaError() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());

        assertThrows(IllegalArgumentException.class,
                () -> customer.rename("  ")
        );
    }

    // ============================================================
    // changeEmail — Cambio de email
    // ============================================================

    @Test
    void changeEmail_EmailValido_Actualiza() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());

        customer.changeEmail("juanperez@example.com");

        assertEquals("juanperez@example.com", customer.getEmail());
    }

    @Test
    void changeEmail_EmailNull_LanzaError() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());

        assertThrows(IllegalArgumentException.class,
                () -> customer.changeEmail(null)
        );
    }

    @Test
    void changeEmail_EmailBlank_LanzaError() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());

        assertThrows(IllegalArgumentException.class,
                () -> customer.changeEmail("  ")
        );
    }

    @Test
    void changeEmail_EmailSinArroba_LanzaError() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());

        assertThrows(IllegalArgumentException.class,
                () -> customer.changeEmail("juanexample.com")
        );
    }

    @Test
    void changeEmail_EmailSinPunto_LanzaError() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());

        assertThrows(IllegalArgumentException.class,
                () -> customer.changeEmail("juan@example")
        );
    }

    // ============================================================
    // changePhone — Sin validación (puede ser null)
    // ============================================================

    @Test
    void changePhone_PhoneValido_Actualiza() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());

        customer.changePhone("987654321");

        assertEquals("987654321", customer.getPhone());
    }

    @Test
    void changePhone_PhoneNull_Actualiza() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                "123456789", null, true, LocalDateTime.now());

        customer.changePhone(null);

        assertNull(customer.getPhone());
    }

    // ============================================================
    // changeAddress — Sin validación (puede ser null)
    // ============================================================

    @Test
    void changeAddress_AddressValido_Actualiza() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());

        customer.changeAddress("Av. Siempre Viva 742");

        assertEquals("Av. Siempre Viva 742", customer.getAddress());
    }

    @Test
    void changeAddress_AddressNull_Actualiza() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, "Calle Falsa 123", true, LocalDateTime.now());

        customer.changeAddress(null);

        assertNull(customer.getAddress());
    }

    // ============================================================
    // Estado (Activo / Inactivo)
    // ============================================================

    @Test
    void deactivate_ClienteActivo_Desactiva() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());

        customer.deactivate();

        assertFalse(customer.isActive());
    }

    @Test
    void deactivate_ClienteYaInactivo_LanzaError() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());
        customer.deactivate();

        assertThrows(BusinessException.class, customer::deactivate);
    }

    @Test
    void activate_ClienteInactivo_Activa() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());
        customer.deactivate();

        customer.activate();

        assertTrue(customer.isActive());
    }

    @Test
    void activate_ClienteYaActivo_LanzaError() {
        Customer customer = new Customer(null, "Juan", "juan@example.com",
                null, null, true, LocalDateTime.now());

        assertThrows(BusinessException.class, customer::activate);
    }
}
