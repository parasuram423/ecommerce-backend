 package com.codegnan.app.ecommercebackendmain.dto;

public class CheckoutRequest {

    private int customerId;

    public CheckoutRequest() {
    }

    public CheckoutRequest(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}