package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.OrderStatisticsAggDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StatisticsService {
    private final OrderService orderService;

    public StatisticsService(OrderService orderService) {
        this.orderService = orderService;
    }

    public List<OrderStatisticsAggDAO> getMonthlyOrderStatistics() {
        return orderService.groupOrdersByMonth();
    }


}
