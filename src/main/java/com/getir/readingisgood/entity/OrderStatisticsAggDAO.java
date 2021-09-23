package com.getir.readingisgood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatisticsAggDAO {
    private String month;
    private int totalOrderCount;
    private int totalBookCount;
    private double totalPurchasedAmount;
}
