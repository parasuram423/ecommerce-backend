 package com.codegnan.app.ecommercebackendmain.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codegnan.app.ecommercebackendmain.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByEmail(String email);

}