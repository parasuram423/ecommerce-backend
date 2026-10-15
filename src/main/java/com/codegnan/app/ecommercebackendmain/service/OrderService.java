 package com.codegnan.app.ecommercebackendmain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codegnan.app.ecommercebackendmain.entity.Order;
import com.codegnan.app.ecommercebackendmain.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(Order order) {

        order.setOrderDate(LocalDateTime.now());

        if (order.getStatus() == null || order.getStatus().isBlank()) {
            order.setStatus("PLACED");
        }

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }
}