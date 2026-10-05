 package com.codegnan.app.ecommercebackendmain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codegnan.app.ecommercebackendmain.entity.Product;
import com.codegnan.app.ecommercebackendmain.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageService productImageService;

    public ProductService(
            ProductRepository productRepository,
            ProductImageService productImageService) {

        this.productRepository = productRepository;
        this.productImageService = productImageService;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {

        LocalDateTime now = LocalDateTime.now();

        product.setCreatedAt(now);
        product.setUpdatedAt(now);

        // Automatically generate product image
        String imageUrl = productImageService.generateProductImage(
                product.getName(),
                product.getBrand()
        );

        product.setImageUrl(imageUrl);

        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {

        Optional<Product> existingProduct =
                productRepository.findById(product.getId());

        if (existingProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        Product existing = existingProduct.get();

        existing.setName(product.getName());
        existing.setBrand(product.getBrand());
        existing.setDescription(product.getDescription());
        existing.setStatus(product.getStatus());
        existing.setImageUrl(product.getImageUrl());

        existing.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(existing);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}