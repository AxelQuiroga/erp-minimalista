package com.example.demo.application.usecase.customer;

import com.example.demo.application.port.out.customer.CustomerRepositoryPort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.model.customer.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateCustomerUseCaseTest {

    @Mock
    private CustomerRepositoryPort customerRepository;

    @InjectMocks
    private CreateCustomerUseCase useCase;

    @Captor
    private ArgumentCaptor<Customer> customerCaptor;

    @Test
    void execute_ConDatosValidos_CreaCliente() {
        given(customerRepository.existsByEmail("juan@example.com")).willReturn(false);
        given(customerRepository.save(any(Customer.class))).willAnswer(invocation -> invocation.getArgument(0));

        Customer result = useCase.execute("Juan Pérez", "juan@example.com", "123456789", "Calle Falsa 123");

        assertAll(
                () -> assertEquals("Juan Pérez", result.getName()),
                () -> assertEquals("juan@example.com", result.getEmail()),
                () -> assertTrue(result.isActive()),
                () -> assertNotNull(result.getCreatedAt())
        );
        verify(customerRepository).save(customerCaptor.capture());
        Customer saved = customerCaptor.getValue();
        assertNull(saved.getId()); // ID null porque aún no se persistió
    }

    @Test
    void execute_EmailDuplicado_LanzaError() {
        given(customerRepository.existsByEmail("juan@example.com")).willReturn(true);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> useCase.execute("Juan Pérez", "juan@example.com", null, null)
        );
        assertTrue(ex.getMessage().contains("ya está registrado"));
        verify(customerRepository, never()).save(any());
    }
}
