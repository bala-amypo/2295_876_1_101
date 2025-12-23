package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockRecordService {

    private final StockRecordRepository stockRepo;
    private final ProductRepository productRepo;
    private final WarehouseRepository warehouseRepo;

    public StockRecordService(
            StockRecordRepository stockRepo,
            ProductRepository productRepo,
            WarehouseRepository warehouseRepo) {
        this.stockRepo = stockRepo;
        this.productRepo = productRepo;
        this.warehouseRepo = warehouseRepo;
    }

    public StockRecord createStockRecord(Long productId, Long warehouseId, StockRecord record) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Warehouse warehouse = warehouseRepo.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        record.setProduct(product);
        record.setWarehouse(warehouse);
        record.setCreatedAt(LocalDateTime.now());

        return stockRepo.save(record);
    }

    public StockRecord getStockRecord(Long id) {
        return stockRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("StockRecord not found"));
    }

    public List<StockRecord> getRecordsByProduct(Long productId) {
        return stockRepo.findByProductId(productId);
    }

    public List<StockRecord> getRecordsByWarehouse(Long warehouseId) {
        return stockRepo.findByWarehouseId(warehouseId);
    }
}
