 package com.codegnan.app.ecommercebackendmain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codegnan.app.ecommercebackendmain.entity.Customer;
import com.codegnan.app.ecommercebackendmain.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(origins = {
	    "http://127.0.0.1:5500",
	    "http://localhost:5500",
	    "http://127.0.0.1:5501",
	    "http://localhost:5501"
	})
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Customer Signup
    @PostMapping("/signup")
    public ResponseEntity<Customer> signup(
            @RequestBody Customer customer) {

        Customer savedCustomer =
                customerService.signup(customer);

        return ResponseEntity.ok(savedCustomer);
    }

    // Customer Signin
    @PostMapping("/signin")
    public ResponseEntity<Customer> signin(
            @RequestBody Customer customer) {

        Customer loggedInCustomer =
                customerService.signin(
                        customer.getEmail(),
                        customer.getPassword()
                );

        return ResponseEntity.ok(loggedInCustomer);
    }
}