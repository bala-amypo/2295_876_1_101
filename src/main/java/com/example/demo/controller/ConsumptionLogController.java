package com.example.demo.controller;

import com.example.demo.model.entity.ConsumptionLog;
import com.example.demo.repository.ConsumptionLogRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consumption-logs")
public class ConsumptionLogController {

    private final ConsumptionLogRepository consumptionLogRepository;

    public ConsumptionLogController(ConsumptionLogRepository consumptionLogRepository) {
        this.consumptionLogRepository = consumptionLogRepository;
    }

    @GetMapping
    public List<ConsumptionLog> getAllLogs() {
        return consumptionLogRepository.findAll();
    }

    @PostMapping
    public ConsumptionLog createLog(@RequestBody ConsumptionLog log) {
        return consumptionLogRepository.save(log);
    }
}
