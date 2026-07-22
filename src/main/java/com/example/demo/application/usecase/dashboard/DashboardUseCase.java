package com.example.demo.application.usecase.dashboard;

import com.example.demo.application.port.in.dashboard.DashboardData;
import com.example.demo.application.port.in.dashboard.DashboardPort;
import com.example.demo.application.port.out.customer.CustomerRepositoryPort;
import com.example.demo.application.port.out.product.ProductRepositoryPort;
import com.example.demo.application.port.out.sale.SaleRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class DashboardUseCase implements DashboardPort {

    private static final int LOW_STOCK_THRESHOLD = 5;

    private final ProductRepositoryPort productRepo;
    private final CustomerRepositoryPort customerRepo;
    private final SaleRepositoryPort saleRepo;

    public DashboardUseCase(ProductRepositoryPort productRepo,
                            CustomerRepositoryPort customerRepo,
                            SaleRepositoryPort saleRepo) {
        this.productRepo = productRepo;
        this.customerRepo = customerRepo;
        this.saleRepo = saleRepo;
    }

    @Override
    public DashboardData execute() {
        long productCount = productRepo.count();
        long customerCount = customerRepo.count();
        long saleCount = saleRepo.count();
        long lowStockCount = productRepo.countByCurrentStockLessThan(LOW_STOCK_THRESHOLD);

        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        long salesToday = saleRepo.countByCreatedAtAfter(todayStart);
        BigDecimal salesTodayAmount = saleRepo.sumTotalAmountByCreatedAtAfter(todayStart);

        var recentSales = saleRepo.findTop5Recent();

        return new DashboardData(
                productCount, customerCount, saleCount,
                salesToday, salesTodayAmount, lowStockCount,
                recentSales
        );
    }
}
