 package com.codegnan.app.ecommercebackendmain.dto;

import java.math.BigDecimal;

public class CustomerDashboardResponse {

    private int customerId;
    private String name;
    private String email;
    private int totalOrders;
    private int cartItems;
    private BigDecimal totalSpent;

    public CustomerDashboardResponse() {
    }

    public CustomerDashboardResponse(
            int customerId,
            String name,
            String email,
            int totalOrders,
            int cartItems,
            BigDecimal totalSpent) {

        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.totalOrders = totalOrders;
        this.cartItems = cartItems;
        this.totalSpent = totalSpent;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getCartItems() {
        return cartItems;
    }

    public void setCartItems(int cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }
}