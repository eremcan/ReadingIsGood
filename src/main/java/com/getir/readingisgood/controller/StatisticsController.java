package com.getir.readingisgood.controller;

import com.getir.readingisgood.entity.OrderStatisticsAggDAO;
import com.getir.readingisgood.service.OrderService;
import com.getir.readingisgood.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@Slf4j
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public ResponseEntity<?> getMonthlyOrderStatistics() {
        List<OrderStatisticsAggDAO> orderStats = statisticsService.getMonthlyOrderStatistics();
        return ResponseEntity.ok(orderStats);
    }

}
