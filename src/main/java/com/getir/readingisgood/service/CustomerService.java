package com.getir.readingisgood.service;


import com.getir.readingisgood.dto.CustomerDto;
import com.getir.readingisgood.dto.request.CustomerRequest;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.exceptions.BookServiceException;
import com.getir.readingisgood.exceptions.CustomerServiceException;
import com.getir.readingisgood.exceptions.generic.ReadingIsGoodException;
import com.getir.readingisgood.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;


    @Autowired
    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper, MessageSource messageSource) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.messageSource = messageSource;
    }


    @Transactional
    public Customer addCustomer(CustomerRequest customerRequest) {
        Optional<Customer> customer = customerRepository.findCustomerByEmail(customerRequest.getEmail());

        if (customer.isEmpty()) {
            Customer newCustomer = new Customer(
                    customerRequest.getFirstName(),
                    customerRequest.getLastName(),
                    customerRequest.getEmail(),
                    customerRequest.getAddress());

            customerRepository.save(newCustomer);
            log.info("New customer is added: " + newCustomer);
            return newCustomer;
        } else {
            throw buildException(CustomerServiceException.Exception.CUSTOMER_ALREADY_EXIST);
        }

    }

    public CustomerDto convertToCustomerDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    private ReadingIsGoodException buildException(CustomerServiceException.Exception exception, Object... params) {
        return new BookServiceException(messageSource.getMessage(exception.getMessage(), params, LocaleContextHolder.getLocale()), exception.getHttpStatus());
    }

}
