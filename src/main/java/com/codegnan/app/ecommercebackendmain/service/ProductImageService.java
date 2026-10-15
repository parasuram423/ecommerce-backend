 
package com.codegnan.app.ecommercebackendmain.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductImageService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateProductImage(String productName, String brand) {
        String name = ((productName == null ? "" : productName)
                + " " + (brand == null ? "" : brand))
                .toLowerCase(Locale.ROOT);

        String category;

        if (name.matches(".*(shoe|sneaker|sandal|boot).*")) {
            category = "shoes";
        } else if (name.matches(".*(shirt|jeans|dress|jacket|hoodie).*")) {
            category = "clothing";
        } else if (name.matches(".*(phone|mobile|iphone).*")) {
            category = "smartphone";
        } else if (name.matches(".*(laptop|computer|keyboard|mouse|headphone).*")) {
            category = "electronics";
        } else if (name.contains("watch")) {
            category = "wristwatch";
        } else if (name.matches(".*(bag|backpack|wallet).*")) {
            category = "handbag";
        } else {
            category = "shopping product";
        }

        return "https://loremflickr.com/600/600/"
                + URLEncoder.encode(category, StandardCharsets.UTF_8);
    }

    public List<Map<String, Object>> fetchReferenceProducts() {
        String url = "https://dummyjson.com/products?limit=0";

        Map<String, Object> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        ).getBody();

        if (response == null || !(response.get("products") instanceof List<?>)) {
            throw new IllegalStateException("Unable to fetch reference products");
        }

        List<?> rawProducts = (List<?>) response.get("products");

        return rawProducts.stream()
                .filter(item -> item instanceof Map<?, ?>)
                .map(item -> {
                    Map<?, ?> map = (Map<?, ?>) item;
                    return (Map<String, Object>) map;
                })
                .toList();
    }
}
