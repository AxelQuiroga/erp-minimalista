package com.example.demo.application.usecase.category;

import com.example.demo.application.port.out.product.CategoryRepositoryPort;
import com.example.demo.application.port.out.product.ProductRepositoryPort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.product.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeactivateCategoryUseCaseTest {

    @Mock
    private CategoryRepositoryPort categoryRepository;

    @Mock
    private ProductRepositoryPort productRepository;

    @InjectMocks
    private DeactivateCategoryUseCase useCase;

    private final Category activeCategory = new Category(1L, "Periféricos", true);

    @Test
    void execute_ConDatosValidos_DesactivaCategoria() {
        given(categoryRepository.findById(1L)).willReturn(Optional.of(activeCategory));
        given(productRepository.hasActiveProductsByCategory(1L)).willReturn(false);
        given(categoryRepository.save(activeCategory)).willReturn(activeCategory);

        Category result = useCase.execute(1L);

        assertFalse(result.isActive());
        verify(categoryRepository).save(activeCategory);
    }

    @Test
    void execute_CategoriaNoEncontrada_LanzaError() {
        given(categoryRepository.findById(99L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> useCase.execute(99L)
        );
    }

    @Test
    void execute_CategoriaConProductosActivos_LanzaError() {
        given(categoryRepository.findById(1L)).willReturn(Optional.of(activeCategory));
        given(productRepository.hasActiveProductsByCategory(1L)).willReturn(true);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> useCase.execute(1L)
        );
        assertTrue(ex.getMessage().contains("tiene productos activos"));
    }
}
