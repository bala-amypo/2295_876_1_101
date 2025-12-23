package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.PredictionService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PredictionServiceImpl implements PredictionService {

    private final StockRecordRepository stockRepo;
    private final ConsumptionLogRepository logRepo;
    private final PredictionRuleRepository ruleRepo;

    public PredictionServiceImpl(StockRecordRepository stockRepo, ConsumptionLogRepository logRepo, PredictionRuleRepository ruleRepo) {
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
        ruleRepo.findByRuleName(rule.getRuleName()).ifPresent(r -> {
            throw new IllegalArgumentException("Rule name must be unique");
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
        StockRecord stock = stockRepo.findById(stockRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("StockRecord not found"));

        List<ConsumptionLog> logs = logRepo.findByStockRecordId(stockRecordId);
        if (logs.isEmpty()) return LocalDate.now().plusDays(1);

        PredictionRule rule = ruleRepo.findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No prediction rule found"));

        LocalDate start = LocalDate.now().minusDays(rule.getAverageDaysWindow());
        double totalUsed = logs.stream()
                .filter(l -> !l.getConsumedDate().isBefore(start))
                .mapToInt(ConsumptionLog::getConsumedQuantity)
                .sum();

        double avgDailyUsage = totalUsed / rule.getAverageDaysWindow();
        avgDailyUsage = Math.max(rule.getMinDailyUsage(), Math.min(avgDailyUsage, rule.getMaxDailyUsage()));

        int daysUntilReorder = (int) Math.ceil((stock.getCurrentQuantity() - stock.getReorderThreshold()) / avgDailyUsage);
        return LocalDate.now().plusDays(daysUntilReorder);
    }
}
