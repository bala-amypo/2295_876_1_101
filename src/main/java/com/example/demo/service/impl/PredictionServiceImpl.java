package com.example.demo.service.impl;

import com.example.demo.model.PredictionRule;
import com.example.demo.service.PredictionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionServiceImpl implements PredictionService {

    @Override
    public PredictionRule createRule(PredictionRule rule) {
        return rule;
    }

    @Override
    public List<PredictionRule> getAllRules() {
        return new ArrayList<>();
    }

    @Override
    public LocalDate predictRestockDate(Long stockRecordId) {
        return LocalDate.now().plusDays(1);
    }
}
