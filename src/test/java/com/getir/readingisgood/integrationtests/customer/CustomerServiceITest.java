package com.getir.readingisgood.integrationtests.customer;


import com.getir.readingisgood.ReadingIsGoodApplication;
import com.getir.readingisgood.dto.request.CustomerRequest;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ReadingIsGoodApplication.class)
public class CustomerServiceITest {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;


    @Test
    public void testAddCustomer() {

        var testEmail = "london@londoner.com";
        Optional<Customer> customer = customerRepository.findCustomerByEmail(testEmail);

        customer.ifPresent(existedCustomer -> customerRepository.deleteById(existedCustomer.getId()));

        CustomerRequest newCustomerRequest = new CustomerRequest("Londoner", "Londoner", testEmail, "London");
        List<Customer> allCustomersBeforeAdd = customerRepository.findAll();

        var expected = allCustomersBeforeAdd.size() + 1;
        customerService.addCustomer(newCustomerRequest);
        List<Customer> allCustomersAfterAdd = customerRepository.findAll();

        var actual = allCustomersAfterAdd.size();
        assertEquals(expected, actual);

    }


}
