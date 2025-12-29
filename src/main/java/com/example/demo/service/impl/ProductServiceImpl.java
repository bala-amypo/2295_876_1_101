package com.example.demo.service.impl;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

    @Override
    public Product createProduct(Product product) {
        return product;
    }

    @Override
    public Product getProduct(Long id) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>();
    }
}
