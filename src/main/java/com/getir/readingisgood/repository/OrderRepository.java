package com.getir.readingisgood.repository;

import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.entity.OrderStatisticsAggDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    Page<Order> findAllByCustomerId(String customerId, Pageable pageable);

    Optional<List<Order>> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);


    @Aggregation(pipeline = {
            "{$group: {_id: { month: { $month: $createdAt }},\n" +
                    "orderCount: {$sum: 1}, totalBookCount: {$sum: $amount},\n" +
                    "totalAmount: {$sum: {$multiply : ['$amount' , '$price']}  }}},\n" +
                    "{$project: { 'month': '$_id.month', '_id': 0,\n" +
                    "'orderCount': '$orderCount','totalBookCount': '$totalBookCount',\n" +
                    "'totalAmount': '$totalAmount'    }}"
    })
    AggregationResults<OrderStatisticsAggDAO> monthlyStatistics();

}
