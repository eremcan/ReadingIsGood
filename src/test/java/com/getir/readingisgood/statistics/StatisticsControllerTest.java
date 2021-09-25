package com.getir.readingisgood.statistics;

import com.getir.readingisgood.controller.StatisticsController;
import com.getir.readingisgood.entity.OrderStatisticsAggDAO;
import com.getir.readingisgood.service.StatisticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {StatisticsController.class})
@ExtendWith(SpringExtension.class)
public class StatisticsControllerTest {
    @Autowired
    StatisticsController statisticsController;
    @MockBean
    StatisticsService statisticsService;

    @Test
    public void testGetMonthlyOrderStats() {
        OrderStatisticsAggDAO orderStatisticsAggDAO = new OrderStatisticsAggDAO();
        orderStatisticsAggDAO.setMonth("3");
        orderStatisticsAggDAO.setTotalOrderCount(10);
        orderStatisticsAggDAO.setTotalPurchasedAmount(2);
        orderStatisticsAggDAO.setTotalBookCount(2);

        when(statisticsService.getMonthlyOrderStatistics()).thenReturn(Collections.singletonList(orderStatisticsAggDAO));
        ResponseEntity<?> responseEntity = statisticsController.getMonthlyOrderStatistics();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }
}

