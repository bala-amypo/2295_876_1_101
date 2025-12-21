package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ConsumptionLog;
import com.example.demo.model.StockRecord;
import com.example.demo.repository.ConsumptionLogRepository;
import com.example.demo.repository.StockRecordRepository;
import com.example.demo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumptionLogServiceImpl implements ConsumptionLogService {

    @Autowired
    private ConsumptionLogRepository consumptionLogRepository;

    @Autowired
    private StockRecordRepository stockRecordRepository;

    @Override
    public ConsumptionLog logConsumption(Long stockRecordId, ConsumptionLog log) {
        StockRecord stockRecord = stockRecordRepository.findById(stockRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("StockRecord not found"));

        if (log.getConsumedQuantity() <= 0) {
            throw new IllegalArgumentException("Consumed quantity must be > 0");
        }

        if (DateUtil.isFutureDate(log.getConsumedDate())) {
            throw new IllegalArgumentException("consumedDate cannot be future");
        }

        log.setStockRecord(stockRecord);
        return consumptionLogRepository.save(log);
    }

    @Override
    public List<ConsumptionLog> getLogsByStockRecord(Long stockRecordId) {
        return consumptionLogRepository.findByStockRecordId(stockRecordId);
    }

    @Override
    public ConsumptionLog getLog(Long id) {
        return consumptionLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ConsumptionLog not found"));
    }
}