 package com.codegnan.app.ecommercebackendmain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codegnan.app.ecommercebackendmain.entity.CartItem;
import com.codegnan.app.ecommercebackendmain.repository.CartItemRepository;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem addToCart(CartItem cartItem) {

        Optional<CartItem> existingItem =
                cartItemRepository.findByCustomerIdAndProductId(
                        cartItem.getCustomerId(),
                        cartItem.getProductId()
                );

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();

            item.setQuantity(
                    item.getQuantity() + cartItem.getQuantity()
            );

            return cartItemRepository.save(item);
        }

        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCustomerCart(int customerId) {

        return cartItemRepository.findByCustomerId(customerId);
    }

    public Optional<CartItem> getCartItemById(int id) {

        return cartItemRepository.findById(id);
    }

    public CartItem updateQuantity(int id, int quantity) {

        Optional<CartItem> existingItem =
                cartItemRepository.findById(id);

        if (existingItem.isEmpty()) {
            throw new RuntimeException("Cart item not found");
        }

        CartItem item = existingItem.get();

        item.setQuantity(quantity);

        return cartItemRepository.save(item);
    }

    public void removeFromCart(int id) {

        if (!cartItemRepository.existsById(id)) {
            throw new RuntimeException("Cart item not found");
        }

        cartItemRepository.deleteById(id);
    }

    public void clearCart(int customerId) {

        List<CartItem> items =
                cartItemRepository.findByCustomerId(customerId);

        cartItemRepository.deleteAll(items);
    }
}