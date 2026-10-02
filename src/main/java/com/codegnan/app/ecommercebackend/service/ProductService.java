 package com.codegnan.app.ecommercebackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codegnan.app.ecommercebackend.entity.Product;
import com.codegnan.app.ecommercebackend.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}