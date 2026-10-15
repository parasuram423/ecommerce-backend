 package com.codegnan.app.ecommercebackendmain.dto;

import java.math.BigDecimal;

public class AdminDashboardResponse {

    private long totalCustomers;
    private long totalProducts;
    private long totalOrders;
    private BigDecimal totalSales;

    public AdminDashboardResponse() {
    }

    public AdminDashboardResponse(
            long totalCustomers,
            long totalProducts,
            long totalOrders,
            BigDecimal totalSales) {

        this.totalCustomers = totalCustomers;
        this.totalProducts = totalProducts;
        this.totalOrders = totalOrders;
        this.totalSales = totalSales;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }
}