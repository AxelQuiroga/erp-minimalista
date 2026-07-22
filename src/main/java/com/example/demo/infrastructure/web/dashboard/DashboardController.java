package com.example.demo.infrastructure.web.dashboard;

import com.example.demo.application.port.in.dashboard.DashboardData;
import com.example.demo.application.port.in.dashboard.DashboardPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardPort dashboardPort;

    public DashboardController(DashboardPort dashboardPort) {
        this.dashboardPort = dashboardPort;
    }

    @GetMapping
    public ResponseEntity<DashboardData> getDashboard() {
        return ResponseEntity.ok(dashboardPort.execute());
    }
}
