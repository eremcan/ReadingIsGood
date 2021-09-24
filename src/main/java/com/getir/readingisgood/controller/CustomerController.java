package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.request.CustomerRequest;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.service.CustomerService;
import com.getir.readingisgood.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customers")
@Slf4j
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;

    @Autowired
    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer newCustomer = customerService.addCustomer(customerRequest);
        return ResponseEntity.ok(customerService.convertToCustomerDto(newCustomer));
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<?> getOrdersByCustomerId(
            @PathVariable("customerId") String customerId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "2") Integer pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId, pageNumber, pageSize, sortBy);
        return ResponseEntity.ok(orders);
    }


}
