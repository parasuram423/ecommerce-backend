
package com.codegnan.app.ecommercebackendmain.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codegnan.app.ecommercebackendmain.dto.AdminDashboardResponse;
import com.codegnan.app.ecommercebackendmain.entity.Admin;
import com.codegnan.app.ecommercebackendmain.repository.AdminRepository;
import com.codegnan.app.ecommercebackendmain.repository.CustomerRepository;
import com.codegnan.app.ecommercebackendmain.repository.OrderRepository;
import com.codegnan.app.ecommercebackendmain.repository.ProductRepository;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(
            AdminRepository adminRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository,
            PasswordEncoder passwordEncoder) {

        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Admin Signin with legacy password migration
    @Transactional
    public Optional<Admin> signin(String email, String password) {

        if (email == null || password == null) {
            return Optional.empty();
        }

        Optional<Admin> result = adminRepository.findByEmail(email);

        if (result.isEmpty()) {
            return Optional.empty();
        }

        Admin admin = result.get();
        String storedPassword = admin.getPassword();

        if (storedPassword == null) {
            return Optional.empty();
        }

        // Existing BCrypt password
        if (storedPassword.startsWith("$2a$")
                || storedPassword.startsWith("$2b$")
                || storedPassword.startsWith("$2y$")) {

            if (passwordEncoder.matches(password, storedPassword)) {
                return Optional.of(admin);
            }

            return Optional.empty();
        }

        // Legacy plain-text password:
        // If it matches, convert it to BCrypt and save.
        if (storedPassword.equals(password)) {
            admin.setPassword(passwordEncoder.encode(password));
            adminRepository.save(admin);
            return Optional.of(admin);
        }

        return Optional.empty();
    }

    // Get Admin By ID
    public Optional<Admin> getAdminById(int id) {
        return adminRepository.findById(id);
    }

    // Admin Dashboard
    public AdminDashboardResponse getDashboard() {

        long totalCustomers = customerRepository.count();
        long totalProducts = productRepository.count();
        long totalOrders = orderRepository.count();

        BigDecimal totalSales = orderRepository.findAll()
                .stream()
                .map(order -> order.getTotalAmount())
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new AdminDashboardResponse(
                totalCustomers,
                totalProducts,
                totalOrders,
                totalSales);
    }
   
    @Transactional
    public boolean resetAdminPassword(String email, String newPassword) {

        if (email == null || email.isBlank()
                || newPassword == null || newPassword.length() < 8) {
            return false;
        }

        Optional<Admin> result = adminRepository.findByEmail(email);

        if (result.isEmpty()) {
            return false;
        }

        Admin admin = result.get();
        admin.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(admin);

        return true;
    }
    
}

