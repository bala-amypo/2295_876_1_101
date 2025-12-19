package com.example.demo.controller;

import com.example.demo.model.entity.PredictionRule;
import com.example.demo.repository.PredictionRuleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prediction-rules")
public class PredictionRuleController {

    private final PredictionRuleRepository predictionRuleRepository;

    public PredictionRuleController(PredictionRuleRepository predictionRuleRepository) {
        this.predictionRuleRepository = predictionRuleRepository;
    }

    @GetMapping
    public List<PredictionRule> getAllRules() {
        return predictionRuleRepository.findAll();
    }

    @PostMapping
    public PredictionRule createRule(@RequestBody PredictionRule rule) {
        return predictionRuleRepository.save(rule);
    }
}
