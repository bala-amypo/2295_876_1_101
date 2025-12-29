package com.example.demo.service.impl;

import com.example.demo.model.StockRecord;
import com.example.demo.model.Product;
import com.example.demo.model.Warehouse;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.service.StockRecordService;
import com.example.demo.repository.WarehouseRepository;
import com.example.demo.repository.StockRecordRepository;
import com.example.demo.repository.ProductRepository;
import java.util.List;
import java.time.LocalDate;

@Service
public class StockRecordServiceImpl implements StockRecordService {

    @Autowired
    private StockRecordRepository stockRecordRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public StockRecord createStockRecord(Long productId, Long warehouseId, StockRecord record) {

        List<StockRecord> existing = stockRecordRepository.findByProductId(productId);
        for (StockRecord sr : existing) {
            if (sr.getWarehouse().getId().equals(warehouseId)) {
                throw new IllegalArgumentException("StockRecord already exists");
            }
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        record.setProduct(product);
        record.setWarehouse(warehouse);

        return stockRecordRepository.save(record);
    }

    @Override
    public StockRecord getStockRecord(Long id) {
        return stockRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StockRecord not found"));
    }

    @Override
    public List<StockRecord> getRecordsBy_product(Long productId) {
        return stockRecordRepository.findByProductId(productId);
    }

    @Override
    public List<StockRecord> getRecordsByWarehouse(Long warehouseId) {
        return stockRecordRepository.findByWarehouseId(warehouseId);
    }
}

