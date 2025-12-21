package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ConsumptionLog;
import com.example.demo.model.PredictionRule;
import com.example.demo.model.StockRecord;
import com.example.demo.repository.ConsumptionLogRepository;
import com.example.demo.repository.PredictionRuleRepository;
import com.example.demo.repository.StockRecordRepository;
import com.example.demo.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PredictionServiceImpl implements PredictionService {

    @Autowired
    private PredictionRuleRepository predictionRuleRepository;

    @Autowired
    private StockRecordRepository stockRecordRepository;

    @Autowired
    private ConsumptionLogRepository consumptionLogRepository;

    @Override
    public LocalDate predictRestockDate(Long stockRecordId) {
        StockRecord stockRecord = stockRecordRepository.findById(stockRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("StockRecord not found"));

        List<ConsumptionLog> logs = consumptionLogRepository.findByStockRecordId(stockRecordId);
        
        if (logs.isEmpty()) {
            return LocalDate.now().plusDays(30); // Default prediction
        }

        // Simple prediction: calculate average daily consumption
        double totalConsumption = logs.stream().mapToInt(ConsumptionLog::getConsumedQuantity).sum();
        double avgDailyConsumption = totalConsumption / Math.max(logs.size(), 1);
        
        if (avgDailyConsumption <= 0) {
            return LocalDate.now().plusDays(30);
        }

        int daysUntilReorder = (int) Math.ceil((stockRecord.getCurrentQuantity() - stockRecord.getReorderThreshold()) / avgDailyConsumption);
        return LocalDate.now().plusDays(Math.max(daysUntilReorder, 1));
    }

    @Override
    public List<PredictionRule> getAllRules() {
        return predictionRuleRepository.findAll();
    }

    @Override
    public PredictionRule createRule(PredictionRule rule) {
        if (rule.getAverageDaysWindow() <= 0) {
            throw new IllegalArgumentException("Average days window must be > 0");
        }
        if (rule.getMinDailyUsage() > rule.getMaxDailyUsage()) {
            throw new IllegalArgumentException("Min daily usage must be <= max daily usage");
        }
        return predictionRuleRepository.save(rule);
    }
}