package com.example.demo.application.usecase;

import com.example.demo.application.port.in.ListSalesPort;
import com.example.demo.domain.model.Sale;
import com.example.demo.domain.repository.SaleRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSalesUseCase implements ListSalesPort {

    private final SaleRepositoryPort saleRepository;

    public ListSalesUseCase(SaleRepositoryPort saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<Sale> execute() {
        return saleRepository.findAll();
    }
}
