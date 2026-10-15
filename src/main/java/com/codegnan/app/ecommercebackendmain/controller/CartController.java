package com.codegnan.app.ecommercebackendmain.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codegnan.app.ecommercebackendmain.entity.CartItem;
import com.codegnan.app.ecommercebackendmain.service.CartService;

@RestController
@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "http://localhost:5500",
        "http://127.0.0.1:5501",
        "http://localhost:5501"
})
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartItem> addToCart(
            @RequestBody CartItem cartItem) {

        return ResponseEntity.ok(
                cartService.addToCart(cartItem)
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CartItem>> getCustomerCart(
            @PathVariable int customerId) {

        return ResponseEntity.ok(
                cartService.getCustomerCart(customerId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateQuantity(
            @PathVariable int id,
            @RequestBody CartItem cartItem) {

        return ResponseEntity.ok(
                cartService.updateQuantity(
                        id,
                        cartItem.getQuantity()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromCart(
            @PathVariable int id) {

        cartService.removeFromCart(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void> clearCart(
            @PathVariable int customerId) {

        cartService.clearCart(customerId);

        return ResponseEntity.noContent().build();
    }
}