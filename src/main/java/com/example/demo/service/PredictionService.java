package com.example.demo.service;

import com.example.demo.model.PredictionRule;
import java.util.List;
import java.time.LocalDate;

public interface PredictionService {
LocalDate predictRestockDate(Long stockRecordId);
List<PredictionRule> getAllRules();
PredictionRule createRule(PredictionRule rule);
}