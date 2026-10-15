 package com.codegnan.app.ecommercebackendmain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codegnan.app.ecommercebackendmain.dto.CheckoutRequest;
import com.codegnan.app.ecommercebackendmain.entity.Order;
import com.codegnan.app.ecommercebackendmain.service.CheckoutService;

@RestController
@CrossOrigin(origins = {
    "http://127.0.0.1:5500",
    "http://localhost:5500",
    "http://127.0.0.1:5501",
    "http://localhost:5501"
})
@RequestMapping("/api/v1/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public ResponseEntity<Order> checkout(
            @RequestBody CheckoutRequest request) {

        Order order = checkoutService.checkout(
                request.getCustomerId()
        );

        return ResponseEntity.ok(order);
    }
}