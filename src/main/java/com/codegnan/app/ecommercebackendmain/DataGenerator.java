 package com.codegnan.app.ecommercebackendmain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.codegnan.app.ecommercebackendmain.entity.Product;
import com.codegnan.app.ecommercebackendmain.repository.ProductRepository;

@Component
public class DataGenerator implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataGenerator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {

        long existingProducts = productRepository.count();

        if (existingProducts >= 10000) {
            System.out.println("10,000 products already exist.");
            return;
        }

        long productsToCreate = 10000 - existingProducts;

        System.out.println(
                "Generating " + productsToCreate + " products..."
        );

        for (int i = 1; i <= productsToCreate; i++) {

            Product product = new Product();

            product.setName("ShopEase Product " + (existingProducts + i));
            product.setBrand("Brand " + ((i % 50) + 1));

            product.setDescription(
                    "High quality product from ShopEase catalog"
            );

            product.setStatus("ACTIVE");

            product.setPrice(
                    BigDecimal.valueOf(
                            100 + ((i * 37) % 4900)
                    )
            );

            product.setImageUrl(
                    "assets/product-default.jpg"
            );

            LocalDateTime now = LocalDateTime.now();

            product.setCreatedAt(now);
            product.setUpdatedAt(now);

            productRepository.save(product);

            if (i % 500 == 0) {
                System.out.println(
                        i + " products generated..."
                );
            }
        }

        System.out.println(
                "10,000 products generation completed!"
        );
    }
}