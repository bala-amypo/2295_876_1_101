package com.example.demo.controller;

import com.example.demo.model.ConsumptionLog;
import com.example.demo.service.ConsumptionLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consumption")
@Tag(name = "Consumption Logs", description = "Consumption logging endpoints")
public class ConsumptionLogController {

    @Autowired
    private ConsumptionLogService consumptionLogService;

    @PostMapping("/{stockRecordId}")
    @Operation(summary = "Log consumption for a stock record")
    public ResponseEntity<ConsumptionLog> logConsumption(
            @PathVariable Long stockRecordId,
            @RequestBody ConsumptionLog log) {
        ConsumptionLog created = consumptionLogService.logConsumption(stockRecordId, log);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/record/{stockRecordId}")
    @Operation(summary = "Get consumption logs by stock record")
    public ResponseEntity<List<ConsumptionLog>> getLogsByStockRecord(@PathVariable Long stockRecordId) {
        List<ConsumptionLog> logs = consumptionLogService.getLogsByStockRecord(stockRecordId);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get consumption log by ID")
    public ResponseEntity<ConsumptionLog> getLog(@PathVariable Long id) {
        ConsumptionLog log = consumptionLogService.getLog(id);
        return ResponseEntity.ok(log);
    }
}