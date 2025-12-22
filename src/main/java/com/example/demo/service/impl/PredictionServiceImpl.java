package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ConsumptionLog;
import com.example.demo.model.PredictionRule;
import com.example.demo.model.StockRecord;
import com.example.demo.repository.ConsumptionLogRepository;
import com.example.demo.repository.PredictionRuleRepository;
import com.example.demo.repository.StockRecordRepository;
import com.example.demo.service.PredictionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PredictionServiceImpl implements PredictionService {

    private final PredictionRuleRepository predictionRuleRepository;
    private final StockRecordRepository stockRecordRepository;
    private final ConsumptionLogRepository consumptionLogRepository;

    public PredictionServiceImpl(PredictionRuleRepository predictionRuleRepository,
                                 StockRecordRepository stockRecordRepository,
                                 ConsumptionLogRepository consumptionLogRepository) {
        this.predictionRuleRepository = predictionRuleRepository;
        this.stockRecordRepository = stockRecordRepository;
        this.consumptionLogRepository = consumptionLogRepository;
    }

    @Override
    public LocalDate predictRestockDate(Long stockRecordId) {
        StockRecord stockRecord = stockRecordRepository.findById(stockRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("StockRecord not found"));

        List<ConsumptionLog> logs = consumptionLogRepository.findByStockRecordId(stockRecordId);
        
        if (logs.isEmpty()) {
            return LocalDate.now().plusDays(30); // Default prediction if no consumption data
        }

        // Get default rule or create one if none exists
        PredictionRule rule = predictionRuleRepository.findAll().stream()
                .findFirst()
                .orElse(PredictionRule.builder()
                        .ruleName("Default")
                        .averageDaysWindow(7)
                        .minDailyUsage(1)
                        .maxDailyUsage(100)
                        .build());

        // Calculate average daily consumption over the window
        LocalDate cutoffDate = LocalDate.now().minusDays(rule.getAverageDaysWindow());
        double totalConsumption = logs.stream()
                .filter(log -> log.getConsumedDate().isAfter(cutoffDate))
                .mapToInt(ConsumptionLog::getConsumedQuantity)
                .sum();

        double averageDailyConsumption = totalConsumption / rule.getAverageDaysWindow();
        
        if (averageDailyConsumption <= 0) {
            return LocalDate.now().plusDays(30);
        }

        // Calculate days until reorder threshold is reached
        int quantityUntilReorder = stockRecord.getCurrentQuantity() - stockRecord.getReorderThreshold();
        int daysUntilRestock = (int) Math.ceil(quantityUntilReorder / averageDailyConsumption);
        
        return LocalDate.now().plusDays(Math.max(1, daysUntilRestock));
    }

    @Override
    public List<PredictionRule> getAllRules() {
        return predictionRuleRepository.findAll();
    }

    @Override
    public PredictionRule createRule(PredictionRule rule) {
        if (rule.getAverageDaysWindow() <= 0) {
            throw new IllegalArgumentException("Average days window must be greater than zero");
        }

        if (rule.getMinDailyUsage() > rule.getMaxDailyUsage()) {
            throw new IllegalArgumentException("Min daily usage must be less than or equal to max daily usage");
        }

        if (predictionRuleRepository.findByRuleName(rule.getRuleName()).isPresent()) {
            throw new IllegalArgumentException("Rule name already exists");
        }

        return predictionRuleRepository.save(rule);
    }
}