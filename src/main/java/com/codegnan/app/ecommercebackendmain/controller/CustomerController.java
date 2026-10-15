 
package com.codegnan.app.ecommercebackendmain.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codegnan.app.ecommercebackendmain.config.JwtService;
import com.codegnan.app.ecommercebackendmain.dto.CustomerDashboardResponse;
import com.codegnan.app.ecommercebackendmain.dto.CustomerResponse;
import com.codegnan.app.ecommercebackendmain.entity.Customer;
import com.codegnan.app.ecommercebackendmain.service.CustomerService;

@RestController
@CrossOrigin(origins = {
    "http://127.0.0.1:5500",
    "http://localhost:5500",
    "http://127.0.0.1:5501",
    "http://localhost:5501",
    "http://localhost:5173",
    "http://127.0.0.1:5173"
})
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final JwtService jwtService;

    public CustomerController(
            CustomerService customerService,
            JwtService jwtService) {
        this.customerService = customerService;
        this.jwtService = jwtService;
    }

    // Customer Signup
    @PostMapping("/signup")
    public ResponseEntity<CustomerResponse> signup(
            @RequestBody Customer customer) {

        Customer savedCustomer = customerService.signup(customer);

        return ResponseEntity.ok(
                new CustomerResponse(
                        savedCustomer.getId(),
                        savedCustomer.getName(),
                        savedCustomer.getEmail(),
                        null
                )
        );
    }

    // Customer Signin
    @PostMapping("/signin")
    public ResponseEntity<CustomerResponse> signin(
            @RequestBody Customer customer) {

        return customerService
                .signin(customer.getEmail(), customer.getPassword())
                .map(savedCustomer -> {
                    String token = jwtService.generateToken(
                            savedCustomer.getEmail(),
                            "CUSTOMER"
                    );

                    return ResponseEntity.ok(
                            new CustomerResponse(
                                    savedCustomer.getId(),
                                    savedCustomer.getName(),
                                    savedCustomer.getEmail(),
                                    token
                            )
                    );
                })
                .orElse(ResponseEntity.status(401).build());
    }

    // Get Customer Profile
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @PathVariable int id) {

        return customerService
                .getCustomerById(id)
                .map(customer ->
                        ResponseEntity.ok(
                                new CustomerResponse(
                                        customer.getId(),
                                        customer.getName(),
                                        customer.getEmail(),
                                        null
                                )
                        )
                )
                .orElse(ResponseEntity.notFound().build());
    }

    // Get All Customers
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        List<CustomerResponse> customers =
                customerService.getAllCustomers()
                        .stream()
                        .map(customer ->
                                new CustomerResponse(
                                        customer.getId(),
                                        customer.getName(),
                                        customer.getEmail(),
                                        null
                                )
                        )
                        .collect(Collectors.toList());

        return ResponseEntity.ok(customers);
    }

    // Customer Dashboard
    @GetMapping("/{id}/dashboard")
    public ResponseEntity<CustomerDashboardResponse> getDashboard(
            @PathVariable int id) {

        return ResponseEntity.ok(
                customerService.getDashboard(id)
        );
    }
}
