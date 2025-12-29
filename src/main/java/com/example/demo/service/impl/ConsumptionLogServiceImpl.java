package com.example.demo.service.impl;

import com.example.demo.model.ConsumptionLog;
import com.example.demo.model.StockRecord;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.service.ConsumptionLogService;
import com.example.demo.repository.ConsumptionLogRepository;
import com.example.demo.repository.StockRecordRepository;
import java.util.List;
import java.time.LocalDate;

@Service
public class ConsumptionLogServiceImpl implements ConsumptionLogService {

    @Autowired
    private ConsumptionLogRepository consumptionLogRepository;

    @Autowired
    private StockRecordRepository stockRecordRepository;

    @Override
    public ConsumptionLog logConsumption(Long stockRecordId, ConsumptionLog log) {

        if (log.getConsumedDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("consumedDate cannot be future");
        }

        StockRecord stockRecord = stockRecordRepository.findById(stockRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("StockRecord not found"));

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
