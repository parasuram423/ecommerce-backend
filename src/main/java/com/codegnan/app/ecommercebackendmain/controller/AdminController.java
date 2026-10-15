 
package com.codegnan.app.ecommercebackendmain.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codegnan.app.ecommercebackendmain.config.JwtService;
import com.codegnan.app.ecommercebackendmain.dto.AdminDashboardResponse;
import com.codegnan.app.ecommercebackendmain.dto.AdminResponse;
import com.codegnan.app.ecommercebackendmain.dto.CustomerResponse;
import com.codegnan.app.ecommercebackendmain.entity.Admin;
import com.codegnan.app.ecommercebackendmain.entity.Order;
import com.codegnan.app.ecommercebackendmain.service.AdminService;
import com.codegnan.app.ecommercebackendmain.service.CustomerService;
import com.codegnan.app.ecommercebackendmain.service.OrderService;

@RestController
@CrossOrigin(origins = {
    "http://127.0.0.1:5500",
    "http://localhost:5500",
    "http://127.0.0.1:5501",
    "http://localhost:5501",
    "http://localhost:5173",
    "http://127.0.0.1:5173"
})
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final JwtService jwtService;

    public AdminController(
            AdminService adminService,
            CustomerService customerService,
            OrderService orderService,
            JwtService jwtService) {

        this.adminService = adminService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    // Admin Login
    @PostMapping("/signin")
    public ResponseEntity<AdminResponse> signin(
            @RequestBody Admin admin) {

        return adminService
                .signin(admin.getEmail(), admin.getPassword())
                .map(savedAdmin -> {
                    String token = jwtService.generateToken(
                            savedAdmin.getEmail(), "ADMIN");

                    return ResponseEntity.ok(
                            new AdminResponse(
                                    savedAdmin.getId(),
                                    savedAdmin.getName(),
                                    savedAdmin.getEmail(),
                                    token));
                })
                .orElse(ResponseEntity.status(401).build());
    }

    // Reset Admin Password
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String email,
            @RequestParam String newPassword) {

        boolean reset = adminService.resetAdminPassword(
                email, newPassword);

        if (reset) {
            return ResponseEntity.ok(
                    "Admin password reset successful");
        }

        return ResponseEntity.badRequest().body(
                "Invalid email or password must contain at least 8 characters");
    }

    // Admin Profile
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponse> getAdminById(
            @PathVariable int id) {

        return adminService
                .getAdminById(id)
                .map(savedAdmin -> ResponseEntity.ok(
                        new AdminResponse(
                                savedAdmin.getId(),
                                savedAdmin.getName(),
                                savedAdmin.getEmail(),
                                null)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Admin Dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboard() {
        return ResponseEntity.ok(adminService.getDashboard());
    }

    // Admin - View All Customers
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        List<CustomerResponse> customers =
                customerService.getAllCustomers()
                        .stream()
                        .map(customer -> new CustomerResponse(
                                customer.getId(),
                                customer.getName(),
                                customer.getEmail(),
                                null))
                        .collect(Collectors.toList());

        return ResponseEntity.ok(customers);
    }

    // Admin - View All Orders
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
