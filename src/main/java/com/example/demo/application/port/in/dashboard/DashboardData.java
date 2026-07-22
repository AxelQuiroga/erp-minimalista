package com.example.demo.application.port.in.dashboard;

import com.example.demo.domain.model.sale.Sale;

import java.math.BigDecimal;
import java.util.List;

public record DashboardData(
        long productCount,
        long customerCount,
        long saleCount,
        long salesToday,
        BigDecimal salesTodayAmount,
        long lowStockCount,
        List<Sale> recentSales
) {}
