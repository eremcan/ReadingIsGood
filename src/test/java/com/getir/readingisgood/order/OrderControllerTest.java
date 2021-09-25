package com.getir.readingisgood.order;

import com.getir.readingisgood.controller.OrderController;
import com.getir.readingisgood.dto.OrderDto;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.service.BookService;
import com.getir.readingisgood.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {OrderController.class, BookService.class})
@ExtendWith(SpringExtension.class)
public class OrderControllerTest {
    @Autowired
    OrderController orderController;
    @MockBean
    private OrderService orderService;
    @MockBean
    private BookService bookService;
    @Mock
    private OrderRepository orderRepository;

    @Test
    public void testConstructor() {
        new OrderController(orderService);
    }

    @Test
    void orderById() {
        //Given
        String orderId = "newOrderId";
        String customerId = "customerId";
        String bookId = "bookId";
        int amount = 1;
        double price = 10.0;

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(orderId);
        orderDto.setAmount(amount);
        orderDto.setBookId(bookId);
        orderDto.setTotalPrice(price);
        orderDto.setCreated(LocalDateTime.of(2020, 1, 1, 10, 10));

        Order order = new Order(customerId, bookId, amount, price);

        when(orderService.getOrderById(orderId)).thenReturn(order);
        when(orderService.convertToOrderDto(order)).thenReturn(orderDto);

        //when
        ResponseEntity<?> responseEntity = orderController.orderById(orderId);

        //then
        verify(orderService).getOrderById(orderId);
        verify(orderService).convertToOrderDto(order);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderDto, responseEntity.getBody());
    }

}
