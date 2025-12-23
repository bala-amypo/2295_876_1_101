package com.example.demo.service;

import com.example.demo.model.ConsumptionLog;
import com.example.demo.model.Product;
import com.example.demo.repository.ConsumptionLogRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConsumptionLogService {

    private final ConsumptionLogRepository logRepository;
    private final ProductRepository productRepository;

    public ConsumptionLogService(
            ConsumptionLogRepository logRepository,
            ProductRepository productRepository) {
        this.logRepository = logRepository;
        this.productRepository = productRepository;
    }

    public ConsumptionLog logConsumption(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ConsumptionLog log = new ConsumptionLog();
        log.setProduct(product);
        log.setQuantity(quantity);
        log.setDate(LocalDate.now());

        return logRepository.save(log);
    }

    public List<ConsumptionLog> getAll() {
        return logRepository.findAll();
    }
}
