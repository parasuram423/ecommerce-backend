 package com.codegnan.app.ecommercebackendmain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codegnan.app.ecommercebackendmain.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCustomerId(int customerId);

    Optional<CartItem> findByCustomerIdAndProductId(
            int customerId,
            int productId
    );
}