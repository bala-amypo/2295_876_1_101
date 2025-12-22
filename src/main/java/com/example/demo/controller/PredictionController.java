package com.example.demo.controller;

import com.example.demo.model.PredictionRule;
import com.example.demo.service.PredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/predict")
@Tag(name = "Predictions", description = "Prediction and rule management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @GetMapping("/restock-date/{stockRecordId}")
    @Operation(summary = "Predict restock date for a stock record")
    public ResponseEntity<LocalDate> predictRestockDate(@PathVariable Long stockRecordId) {
        LocalDate restockDate = predictionService.predictRestockDate(stockRecordId);
        return ResponseEntity.ok(restockDate);
    }

    @PostMapping("/rules")
    @Operation(summary = "Create a new prediction rule")
    public ResponseEntity<PredictionRule> createRule(@RequestBody PredictionRule rule) {
        PredictionRule created = predictionService.createRule(rule);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/rules")
    @Operation(summary = "Get all prediction rules")
    public ResponseEntity<List<PredictionRule>> getAllRules() {
        List<PredictionRule> rules = predictionService.getAllRules();
        return ResponseEntity.ok(rules);
    }
}