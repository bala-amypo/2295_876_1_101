package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.PredictionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class PredictionServiceImpl implements PredictionService {

    private final StockRecordRepository stockRepo;
    private final ConsumptionLogRepository logRepo;
    private final PredictionRuleRepository ruleRepo;

    public PredictionServiceImpl(
            StockRecordRepository stockRepo,
            ConsumptionLogRepository logRepo,
            PredictionRuleRepository ruleRepo) {

        this.stockRepo = stockRepo;
        this.logRepo = logRepo;
        this.ruleRepo = ruleRepo;
    }

    @Override
    public PredictionRule createRule(PredictionRule rule) {

        if (rule.getAverageDaysWindow() <= 0)
            throw new IllegalArgumentException("averageDaysWindow must be > 0");

        if (rule.getMinDailyUsage() > rule.getMaxDailyUsage())
            throw new IllegalArgumentException("minDailyUsage must be <= maxDailyUsage");

        ruleRepo.findByRuleName(rule.getRuleName())
                .ifPresent(r -> {
                    throw new IllegalArgumentException("ruleName already exists");
                });

        rule.setCreatedAt(LocalDateTime.now());
        return ruleRepo.save(rule);
    }

    @Override
    public List<PredictionRule> getAllRules() {
        return ruleRepo.findAll();
    }

    @Override
    public LocalDate predictRestockDate(Long stockRecordId) {

        StockRecord record = stockRepo.findById(stockRecordId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("StockRecord not found"));

        List<ConsumptionLog> logs =
                logRepo.findByStockRecordId(stockRecordId);

        if (logs.isEmpty()) {
            return LocalDate.now();
        }

        // Use the most recently created rule, or default logic
        PredictionRule rule = ruleRepo.findAll().stream()
                .max(Comparator.comparing(PredictionRule::getCreatedAt))
                .orElse(null);

        int window = rule != null ? rule.getAverageDaysWindow() : 7;

        LocalDate cutoff = LocalDate.now().minusDays(window);

        double avgDailyUsage = logs.stream()
                .filter(l -> !l.getConsumedDate().isBefore(cutoff))
                .mapToInt(ConsumptionLog::getConsumedQuantity)
                .average()
                .orElse(0);

        if (avgDailyUsage <= 0) {
            return LocalDate.now();
        }

        int remainingQty =
                record.getCurrentQuantity() - record.getReorderThreshold();

        int daysLeft = (int) Math.ceil(remainingQty / avgDailyUsage);

        return LocalDate.now().plusDays(Math.max(daysLeft, 0));
    }
}
