 
package com.codegnan.app.ecommercebackendmain.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codegnan.app.ecommercebackendmain.entity.Product;
import com.codegnan.app.ecommercebackendmain.service.ProductService;

@RestController
@CrossOrigin(origins = {
    "http://127.0.0.1:5500",
    "http://localhost:5500",
    "http://127.0.0.1:5501",
    "http://localhost:5501",
    "http://localhost:5173"
})
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get paginated products
    // Example: /api/v1/products/paged?page=0&size=20
    @GetMapping("/paged")
    public ResponseEntity<Page<Product>> getProductsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        if (page < 0) {
            page = 0;
        }

        if (size < 1) {
            size = 20;
        }

        if (size > 100) {
            size = 100;
        }

        return ResponseEntity.ok(
                productService.getProductsPaged(page, size));
    }

    // Search products by name
    // Example: /api/v1/products/search?name=laptop
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam String name) {

        return ResponseEntity.ok(
                productService.searchProducts(name));
    }

    // Filter products by brand
    // Example: /api/v1/products/filter/brand?brand=HP
    @GetMapping("/filter/brand")
    public ResponseEntity<List<Product>> filterByBrand(
            @RequestParam String brand) {

        return ResponseEntity.ok(
                productService.filterProductsByBrand(brand));
    }

    // Filter products by price range
    // Example: /api/v1/products/filter/price?min=1000&max=50000
    @GetMapping("/filter/price")
    public ResponseEntity<List<Product>> filterByPrice(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {

        if (min.compareTo(BigDecimal.ZERO) < 0
                || max.compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.badRequest().build();
        }

        if (min.compareTo(max) > 0) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                productService.filterProductsByPrice(min, max));
    }

    // Sort products by price: low to high
    @GetMapping("/sort/price-asc")
    public ResponseEntity<List<Product>> sortByPriceAsc() {
        return ResponseEntity.ok(
                productService.sortProductsByPriceAsc());
    }

    // Sort products by price: high to low
    @GetMapping("/sort/price-desc")
    public ResponseEntity<List<Product>> sortByPriceDesc() {
        return ResponseEntity.ok(
                productService.sortProductsByPriceDesc());
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @PathVariable int id) {

        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Add product
    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestBody Product product) {

        return ResponseEntity.ok(
                productService.addProduct(product));
    }

    // Update product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable int id,
            @RequestBody Product product) {

        if (productService.getProductById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        product.setId(id);

        return ResponseEntity.ok(
                productService.updateProduct(product));
    }

    // Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable int id) {

        if (productService.getProductById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    // Update products with missing/default images
    @PostMapping("/update-missing-images")
    public ResponseEntity<String> updateMissingProductImages() {

        int updatedCount =
                productService.updateMissingProductImages();

        return ResponseEntity.ok(
                "Product images updated: " + updatedCount);
    }
}
