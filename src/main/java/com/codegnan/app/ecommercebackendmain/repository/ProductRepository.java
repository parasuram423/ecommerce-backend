 package com.codegnan.app.ecommercebackendmain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codegnan.app.ecommercebackendmain.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}