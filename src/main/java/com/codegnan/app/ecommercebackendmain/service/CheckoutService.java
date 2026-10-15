 package com.codegnan.app.ecommercebackendmain.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codegnan.app.ecommercebackendmain.entity.CartItem;
import com.codegnan.app.ecommercebackendmain.entity.Order;
import com.codegnan.app.ecommercebackendmain.entity.OrderItem;
import com.codegnan.app.ecommercebackendmain.entity.Product;
import com.codegnan.app.ecommercebackendmain.repository.ProductRepository;

@Service
public class CheckoutService {

    private final CartService cartService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final ProductRepository productRepository;

    public CheckoutService(
            CartService cartService,
            OrderService orderService,
            OrderItemService orderItemService,
            ProductRepository productRepository) {

        this.cartService = cartService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order checkout(int customerId) {

        List<CartItem> cartItems = cartService.getCustomerCart(customerId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;

        // Calculate total
        for (CartItem cartItem : cartItems) {

            Product product = productRepository
                    .findById(cartItem.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found: "
                                    + cartItem.getProductId()));

            BigDecimal itemTotal = product.getPrice()
                    .multiply(
                            BigDecimal.valueOf(cartItem.getQuantity())
                    );

            totalAmount = totalAmount.add(itemTotal);
        }

        // Create Order
        Order order = new Order();

        order.setCustomerId(customerId);
        order.setTotalAmount(totalAmount);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderService.placeOrder(order);

        // Create Order Items
        for (CartItem cartItem : cartItems) {

            Product product = productRepository
                    .findById(cartItem.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found: "
                                    + cartItem.getProductId()));

            OrderItem orderItem = new OrderItem();

            orderItem.setOrderId(savedOrder.getId());
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItemService.addOrderItem(orderItem);
        }

        // Clear customer's cart
        cartService.clearCart(customerId);

        return savedOrder;
    }
}