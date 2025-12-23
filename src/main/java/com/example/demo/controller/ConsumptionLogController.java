package com.example.demo.controller;

import com.example.demo.model.ConsumptionLog;
import com.example.demo.service.ConsumptionLogService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/consumption")
public class ConsumptionLogController {

    private final ConsumptionLogService service;

    public ConsumptionLogController(ConsumptionLogService service) {
        this.service = service;
    }

    @PostMapping("/{stockRecordId}")
    public ConsumptionLog logConsumption(@PathVariable Long stockRecordId, @RequestBody ConsumptionLog log) {
        return service.logConsumption(stockRecordId, log);
    }

    @GetMapping("/{id}")
    public ConsumptionLog getLog(@PathVariable Long id) {
        return service.getLog(id);
    }

    @GetMapping("/record/{stockRecordId}")
    public List<ConsumptionLog> getLogsByStockRecord(@PathVariable Long stockRecordId) {
        return service.getLogsByStockRecord(stockRecordId);
    }
}
