package com.getir.readingisgood.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMonthsAggDAO {
    public int month;
    public int orderCount;
}
