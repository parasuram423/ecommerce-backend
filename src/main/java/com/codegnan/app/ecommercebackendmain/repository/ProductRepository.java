
package com.codegnan.app.ecommercebackendmain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codegnan.app.ecommercebackendmain.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Search products by name
    List<Product> findByNameContainingIgnoreCase(String name);

    // Search products by brand
    List<Product> findByBrandContainingIgnoreCase(String brand);

    // Filter products within a price range
    List<Product> findByPriceBetween(
            BigDecimal minPrice,
            BigDecimal maxPrice);

    // Sort products by price
    List<Product> findAllByOrderByPriceAsc();

    List<Product> findAllByOrderByPriceDesc();

    // Search products with pagination
    Page<Product> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable);
}
