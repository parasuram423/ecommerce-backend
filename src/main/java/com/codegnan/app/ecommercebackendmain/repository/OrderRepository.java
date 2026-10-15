 package com.codegnan.app.ecommercebackendmain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codegnan.app.ecommercebackendmain.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomerId(int customerId);
}