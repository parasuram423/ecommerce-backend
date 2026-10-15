 package com.codegnan.app.ecommercebackendmain.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codegnan.app.ecommercebackendmain.entity.OrderItem;
import com.codegnan.app.ecommercebackendmain.service.OrderItemService;

@RestController
@CrossOrigin(origins = {
    "http://127.0.0.1:5500",
    "http://localhost:5500",
    "http://127.0.0.1:5501",
    "http://localhost:5501"
})
@RequestMapping("/api/v1/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public ResponseEntity<OrderItem> addOrderItem(
            @RequestBody OrderItem orderItem) {

        return ResponseEntity.ok(
                orderItemService.addOrderItem(orderItem)
        );
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {

        return ResponseEntity.ok(
                orderItemService.getAllOrderItems()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(
            @PathVariable int id) {

        return orderItemService.getOrderItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getItemsByOrderId(
            @PathVariable int orderId) {

        return ResponseEntity.ok(
                orderItemService.getItemsByOrderId(orderId)
        );
    }
}