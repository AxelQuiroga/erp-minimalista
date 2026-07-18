package com.example.demo.application.usecase;

import com.example.demo.application.port.in.GetSalePort;
import com.example.demo.domain.exception.BusinessException;
import com.example.demo.domain.model.Sale;
import com.example.demo.domain.repository.SaleRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class GetSaleUseCase implements GetSalePort {

    private final SaleRepositoryPort saleRepository;

    public GetSaleUseCase(SaleRepositoryPort saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public Sale execute(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Venta no encontrada: " + id));
    }
}
