package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        if (product.getProductName() == null || product.getProductName().isBlank())
            throw new IllegalArgumentException("productName cannot be blank");

        productRepository.findBySku(product.getSku()).ifPresent(p -> {
            throw new IllegalArgumentException("SKU already exists");
        });

        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
