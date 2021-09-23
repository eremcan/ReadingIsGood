package com.getir.readingisgood.service;


import com.getir.readingisgood.dto.OrderDto;
import com.getir.readingisgood.dto.request.BookUpdateRequest;
import com.getir.readingisgood.dto.request.OrderRequest;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.entity.OrderMonthsAggDAO;
import com.getir.readingisgood.entity.OrderStatisticsAggDAO;
import com.getir.readingisgood.exceptions.BookServiceException;
import com.getir.readingisgood.exceptions.OrderServiceException;
import com.getir.readingisgood.exceptions.generic.ReadingIsGoodException;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final MongoTemplate mongoTemplate;
    private final MessageSource messageSource;


    @Autowired
    public OrderService(OrderRepository orderRepository, BookService bookService, ModelMapper modelMapper, MongoTemplate mongoTemplate, MessageSource messageSource) {
        this.orderRepository = orderRepository;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.mongoTemplate = mongoTemplate;
        this.messageSource = messageSource;
    }

    @Transactional
    public Order addOrder(OrderRequest orderRequest) {
        Book orderedBook = bookService.getBookById(orderRequest.getBookId());
        Integer bookStock = orderedBook.getStock();

        checkNumberOfBookAmounts(orderRequest.getAmount());
        checkDemandedBooksAndStock(orderRequest.getAmount(), bookStock);

        Order order = new Order(orderRequest.getCustomerId(), orderRequest.getBookId(), orderRequest.getAmount(), orderedBook.getPrice() * orderRequest.getAmount());
        orderRepository.save(order);

        bookService.updateBookStockForOrder(new BookUpdateRequest(orderRequest.getBookId(), -orderRequest.getAmount()));
        log.info("order added -> : " + order);

        return order;
    }


    public List<Order> getOrdersByCustomerId(String customerId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Order> customerOrders = orderRepository.findAllByCustomerId(customerId, paging);

        if (customerOrders.hasContent()) {
            return customerOrders.getContent();
        }
        throw buildException(OrderServiceException.Exception.CUSTOMER_HAS_NO_ORDER, customerId);
    }


    public List<Order> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate) {
        Optional<List<Order>> ordersByDate = orderRepository.findAllByCreatedAtBetween(startDate, endDate);

        if (ordersByDate.isPresent()) {
            log.info("Orders in between " + startDate + "-" + endDate + ": " + ordersByDate.get());
            return ordersByDate.get();
        } else {
            throw buildException(OrderServiceException.Exception.DATE_IN_BETWEEN_NOT_FOUND, startDate, endDate);
        }
    }


    public Order getOrderById(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            return order.get();
        } else {
            throw buildException(OrderServiceException.Exception.ORDER_NOT_FOUND, orderId);
        }
    }


    public List<OrderMonthsAggDAO> groupOrderMonths() {

        Aggregation agg = newAggregation(
                project("orderId").and(DateOperators.Month.month("$created")).as("month"),
                group("month").count().as("orderCount"),
                project("orderCount").and("month").previousOperation()
        );

        AggregationResults<OrderMonthsAggDAO> results = mongoTemplate.aggregate(agg,
                "orders", OrderMonthsAggDAO.class);
        List<OrderMonthsAggDAO> orderMonthList = results.getMappedResults();

        log.info(orderMonthList.toString());

        return orderMonthList;
    }

    public List<OrderStatisticsAggDAO> groupOrdersByMonth() {
        List<OrderStatisticsAggDAO> orderStats = orderRepository.monthlyStatistics().getMappedResults();

        List<OrderMonthsAggDAO> orderMonthsDtoList = groupOrderMonths();

        int months = 0;
        for (OrderStatisticsAggDAO order : orderStats) {
            int id = orderMonthsDtoList.get(months).getMonth();
            order.setMonth(Utils.convertMonthToName(id));
            months++;
        }

        log.info(orderStats.toString());
        return orderStats;
    }


    public void checkNumberOfBookAmounts(int numberOfBooks) throws ReadingIsGoodException {
        if (numberOfBooks < 1) {
            throw buildException(OrderServiceException.Exception.ORDER_INVALID_NUMBER);
        }
    }

    public void checkDemandedBooksAndStock(Integer numberOfDemandedBooks, Integer stock) throws ReadingIsGoodException {
        if (numberOfDemandedBooks > stock) {
            throw buildException(OrderServiceException.Exception.INSUFFICIENT_STOCK, numberOfDemandedBooks);
        }
    }


    public OrderDto convertToOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    private ReadingIsGoodException buildException(OrderServiceException.Exception exception, Object... params) {
        return new BookServiceException(messageSource.getMessage(exception.getMessage(), params, LocaleContextHolder.getLocale()), exception.getHttpStatus());
    }
}
