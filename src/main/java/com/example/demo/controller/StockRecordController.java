package com.example.demo.controller;

import com.example.demo.model.entity.StockRecord;
import com.example.demo.repository.StockRecordRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-records")
public class StockRecordController {

    private final StockRecordRepository stockRecordRepository;

    public StockRecordController(StockRecordRepository stockRecordRepository) {
        this.stockRecordRepository = stockRecordRepository;
    }

    @GetMapping
    public List<StockRecord> getAllStockRecords() {
        return stockRecordRepository.findAll();
    }

    @PostMapping
    public StockRecord createStockRecord(@RequestBody StockRecord stockRecord) {
        return stockRecordRepository.save(stockRecord);
    }
}
