package com.example.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.service.PredictionService;
import com.example.demo.model.PredictionRule;
import java.util.List;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@RestController
@RequestMapping("/api/predict")
@Tag(name = "Prediction")
@SecurityRequirement(name="bearerAuth")
public class PredictionController {


private final PredictionService predictionService;


public PredictionController(PredictionService predictionService) {
this.predictionService = predictionService;
}


@GetMapping("/restock-date/{stockRecordId}")
public LocalDate predict(@PathVariable Long stockRecordId) {
return predictionService.predictRestockDate(stockRecordId);
}


@PostMapping("/rules")
public PredictionRule createRule(@RequestBody PredictionRule rule) {
return predictionService.createRule(rule);
}


@GetMapping("/rules")
public List<PredictionRule> getRules() {
return predictionService.getAllRules();
}
}