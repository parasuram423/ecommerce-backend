 package com.codegnan.app.ecommercebackendmain.service;

import org.springframework.stereotype.Service;

@Service
public class ProductImageService {

    public String generateProductImage(String productName, String brand) {

        String text = productName + " " + brand;

        System.out.println("Generating image for: " + text);

        // Temporary image-generation logic
        // AI image generation will be connected next.

        return "assets/product-default.jpg";
    }
}