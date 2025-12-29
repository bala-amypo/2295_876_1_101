package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
Optional<Product> findBySku(String sku);
}