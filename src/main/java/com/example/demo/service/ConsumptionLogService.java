package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ConsumptionLogService {

    private final ConsumptionLogRepository logRepo;
    private final StockRecordRepository stockRepo;

    public ConsumptionLogService(ConsumptionLogRepository logRepo, StockRecordRepository stockRepo) {
        this.logRepo = logRepo;
        this.stockRepo = stockRepo;
    }

    public ConsumptionLog logConsumption(Long stockRecordId, ConsumptionLog log) {
        StockRecord stock = stockRepo.findById(stockRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("StockRecord not found"));

        if (log.getConsumedQuantity() <= 0)
            throw new IllegalArgumentException("consumedQuantity must be > 0");
        if (log.getConsumedDate().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("consumedDate cannot be future");

        log.setStockRecord(stock);
        return logRepo.save(log);
    }

    public List<ConsumptionLog> getLogsByStockRecord(Long stockRecordId) {
        return logRepo.findByStockRecordId(stockRecordId);
    }

    public ConsumptionLog getLog(Long id) {
        return logRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("ConsumptionLog not found"));
    }
}
