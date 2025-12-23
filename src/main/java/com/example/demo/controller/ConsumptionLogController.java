package com.example.demo.controller;

import com.example.demo.model.ConsumptionLog;
import com.example.demo.service.ConsumptionLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumption")
public class ConsumptionLogController {

    private final ConsumptionLogService service;

    // Manual constructor for dependency injection
    public ConsumptionLogController(ConsumptionLogService service) {
        this.service = service;
    }

    @PostMapping("/{stockId}/{qty}")
    public ConsumptionLog log(@PathVariable Long stockId,
                              @PathVariable int qty) {
        return service.logConsumption(stockId, qty);
    }

    @GetMapping
    public List<ConsumptionLog> getAll() {
        return service.getAll();
    }
}
