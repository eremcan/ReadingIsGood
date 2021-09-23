package com.getir.readingisgood.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("customers")
public class Customer {

    @Id
    private String id;

    private String firstName;
    private String lastName;

    private String email;
    private String address;

    private LocalDateTime created;


    public Customer(String firstName, String lastName, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.created = LocalDateTime.now();
    }
}
