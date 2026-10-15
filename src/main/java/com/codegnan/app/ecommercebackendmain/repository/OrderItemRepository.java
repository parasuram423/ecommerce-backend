 package com.codegnan.app.ecommercebackendmain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codegnan.app.ecommercebackendmain.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrderId(int orderId);
}