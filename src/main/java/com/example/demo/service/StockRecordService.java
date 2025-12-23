package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockRecordService {

    private final StockRecordRepository stockRepo;
    private final ProductRepository productRepo;
    private final WarehouseRepository warehouseRepo;

    public StockRecordService(StockRecordRepository stockRepo, ProductRepository productRepo, WarehouseRepository warehouseRepo) {
        this.stockRepo = stockRepo;
        this.productRepo = productRepo;
        this.warehouseRepo = warehouseRepo;
    }

    public StockRecord createStockRecord(Long productId, Long warehouseId, StockRecord record) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Warehouse warehouse = warehouseRepo.findById(warehouseId).orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        stockRepo.findByProductIdAndWarehouseId(productId, warehouseId).ifPresent(r -> {
            throw new IllegalArgumentException("StockRecord already exists");
        });

        if (record.getCurrentQuantity() < 0)
            throw new IllegalArgumentException("currentQuantity must be >= 0");
        if (record.getReorderThreshold() <= 0)
            throw new IllegalArgumentException("reorderThreshold must be > 0");

        record.setProduct(product);
        record.setWarehouse(warehouse);
        record.setLastUpdated(LocalDateTime.now());

        return stockRepo.save(record);
    }

    public StockRecord getStockRecord(Long id) {
        return stockRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("StockRecord not found"));
    }

    public List<StockRecord> getRecordsByProduct(Long productId) {
        return stockRepo.findByProductId(productId);
    }

    public List<StockRecord> getRecordsByWarehouse(Long warehouseId) {
        return stockRepo.findByWarehouseId(warehouseId);
    }
}
