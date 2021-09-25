package com.getir.readingisgood.customer;

import com.getir.readingisgood.controller.CustomerController;
import com.getir.readingisgood.dto.request.CustomerRequest;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.service.BookService;
import com.getir.readingisgood.service.CustomerService;
import com.getir.readingisgood.service.OrderService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    BookService bookService;
    @MockBean
    private CustomerService customerService;
    @Autowired
    private CustomerController customerController;

    @Test
    public void testConstructor() {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        new CustomerController(new CustomerService(customerRepository, new ModelMapper(), null),
                new OrderService(orderRepository, bookService, new ModelMapper(), null, null));
    }

    @Test
    public void testAddCustomer() {
        CustomerRepository customerRepository = mock(CustomerRepository.class);

        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(Optional.empty());
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository, new ModelMapper(), null),
                new OrderService(orderRepository, bookService, new ModelMapper(), null, null));
        customerController.addCustomer(new CustomerRequest("Dosto", "Yevski", "dostoyevski@gmail.com", "Deneme Adres"));
        verify(customerRepository).findCustomerByEmail(anyString());
    }


}
