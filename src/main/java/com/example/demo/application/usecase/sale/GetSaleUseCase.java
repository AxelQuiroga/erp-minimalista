package com.example.demo.application.usecase.sale;

import com.example.demo.application.port.in.sale.GetSalePort;
import com.example.demo.domain.exception.NotFoundException;
import com.example.demo.domain.model.sale.Sale;
import com.example.demo.application.port.out.sale.SaleRepositoryPort;
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
                .orElseThrow(() -> new NotFoundException("Venta no encontrada: " + id));
    }
}
