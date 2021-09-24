package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.request.OrderRequest;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/{Id}")
    public ResponseEntity<?> orderById(@PathVariable("Id") String orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderService.convertToOrderDto(order));
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.addOrder(orderRequest);
        return ResponseEntity.ok(orderService.convertToOrderDto(order));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listOrders(
            @PathParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        List<Order> orderList = orderService.getOrdersByDate(startDate, endDate);
        orderService.groupOrdersByMonth();
        return ResponseEntity.ok(orderList);

    }
}
