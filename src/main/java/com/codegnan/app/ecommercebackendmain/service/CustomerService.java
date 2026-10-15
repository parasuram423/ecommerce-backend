 
package com.codegnan.app.ecommercebackendmain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codegnan.app.ecommercebackendmain.dto.CustomerDashboardResponse;
import com.codegnan.app.ecommercebackendmain.entity.CartItem;
import com.codegnan.app.ecommercebackendmain.entity.Customer;
import com.codegnan.app.ecommercebackendmain.entity.Order;
import com.codegnan.app.ecommercebackendmain.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(
            CustomerRepository customerRepository,
            OrderService orderService,
            CartService cartService,
            PasswordEncoder passwordEncoder) {

        this.customerRepository = customerRepository;
        this.orderService = orderService;
        this.cartService = cartService;
        this.passwordEncoder = passwordEncoder;
    }

    // Customer Signup
    @Transactional
    public Customer signup(Customer customer) {

        if (customer.getPassword() == null
                || customer.getPassword().isBlank()) {
            throw new IllegalArgumentException(
                    "Password cannot be empty");
        }

        customer.setPassword(
                passwordEncoder.encode(customer.getPassword()));

        return customerRepository.save(customer);
    }

    // Customer Signin
    public Optional<Customer> signin(
            String email, String password) {

        if (email == null || password == null) {
            return Optional.empty();
        }

        return customerRepository.findByEmail(email)
                .filter(customer ->
                        customer.getPassword() != null
                        && passwordEncoder.matches(
                                password,
                                customer.getPassword()));
    }

    // Get Customer By ID
    public Optional<Customer> getCustomerById(int id) {
        return customerRepository.findById(id);
    }

    // Get All Customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Customer Dashboard
    public CustomerDashboardResponse getDashboard(
            int customerId) {

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found"));

        List<Order> orders =
                orderService.getOrdersByCustomerId(customerId);

        List<CartItem> cartItems =
                cartService.getCustomerCart(customerId);

        BigDecimal totalSpent = BigDecimal.ZERO;

        for (Order order : orders) {
            if (order.getTotalAmount() != null) {
                totalSpent = totalSpent.add(
                        order.getTotalAmount());
            }
        }

        return new CustomerDashboardResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                orders.size(),
                cartItems.size(),
                totalSpent);
    }
}
