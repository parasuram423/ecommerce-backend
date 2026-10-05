 package com.codegnan.app.ecommercebackendmain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codegnan.app.ecommercebackendmain.dao.CustomerDao;
import com.codegnan.app.ecommercebackendmain.entity.Customer;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    // Customer Signup
    public Customer signup(Customer customer) {

        if (customerDao.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        return customerDao.save(customer);
    }

    // Customer Signin
    public Customer signin(String email, String password) {

        Optional<Customer> customer =
                customerDao.findByEmail(email);

        if (customer.isPresent()
                && customer.get().getPassword().equals(password)) {

            return customer.get();
        }

        throw new RuntimeException("Invalid email or password");
    }
}