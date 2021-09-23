package com.getir.readingisgood.repository;

import com.getir.readingisgood.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface CustomerRepository extends MongoRepository<Customer, String> {

    Optional<Customer> findCustomerByEmail(String email);
}
