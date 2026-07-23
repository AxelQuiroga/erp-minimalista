package com.example.demo.application.usecase.product;

import com.example.demo.application.port.out.product.CategoryRepositoryPort;
import com.example.demo.application.port.out.product.ProductRepositoryPort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.product.Category;
import com.example.demo.domain.model.product.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {

    @Mock
    private ProductRepositoryPort productRepository;

    @Mock
    private CategoryRepositoryPort categoryRepository;

    @InjectMocks
    private CreateProductUseCase useCase;

    private final Product validProduct = new Product(
            null, 1L, "Teclado Mecánico", "TEC-001",
            new BigDecimal("100"), new BigDecimal("150"),
            10, true
    );

    private final Category activeCategory = new Category(1L, "Periféricos", true);

    @Test
    void createProduct_ConDatosValidos_CreaProducto() {
        given(categoryRepository.findById(1L)).willReturn(Optional.of(activeCategory));
        given(productRepository.existsBySku("TEC-001")).willReturn(false);
        given(productRepository.save(any(Product.class))).willAnswer(invocation -> invocation.getArgument(0));

        Product result = useCase.createProduct(validProduct);

        assertAll(
                () -> assertEquals("Teclado Mecánico", result.getName()),
                () -> assertEquals("TEC-001", result.getSku())
        );
        verify(productRepository).save(validProduct);
    }

    @Test
    void createProduct_SkuDuplicado_LanzaError() {
        given(productRepository.existsBySku("TEC-001")).willReturn(true);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> useCase.createProduct(validProduct)
        );
        assertTrue(ex.getMessage().contains("SKU duplicado"));
        verify(productRepository, never()).save(any());
    }

    @Test
    void createProduct_CategoriaNoEncontrada_LanzaError() {
        given(categoryRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> useCase.createProduct(validProduct)
        );
        verify(productRepository, never()).save(any());
    }

    @Test
    void createProduct_CategoriaInactiva_LanzaError() {
        given(categoryRepository.findById(1L)).willReturn(Optional.of(new Category(1L, "Inactiva", false)));

        BusinessException ex = assertThrows(BusinessException.class,
                () -> useCase.createProduct(validProduct)
        );
        assertTrue(ex.getMessage().contains("desactivada"));
        verify(productRepository, never()).save(any());
    }
}
