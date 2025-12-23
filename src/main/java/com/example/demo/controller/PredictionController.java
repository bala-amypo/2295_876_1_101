package com.example.demo.controller;

import com.example.demo.model.PredictionRule;
import com.example.demo.service.PredictionService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/predict")
public class PredictionController {

    private final PredictionService service;

    public PredictionController(PredictionService service) {
        this.service = service;
    }

    @PostMapping("/rules")
    public PredictionRule createRule(@RequestBody PredictionRule rule) {
        return service.createRule(rule);
    }

    @GetMapping("/rules")
    public List<PredictionRule> getAllRules() {
        return service.getAllRules();
    }

    @GetMapping("/restock-date/{stockRecordId}")
    public LocalDate predictRestockDate(@PathVariable Long stockRecordId) {
        return service.predictRestockDate(stockRecordId);
    }
}
