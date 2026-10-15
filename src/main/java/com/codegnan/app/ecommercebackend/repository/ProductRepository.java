 package com.codegnan.app.ecommercebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codegnan.app.ecommercebackend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}