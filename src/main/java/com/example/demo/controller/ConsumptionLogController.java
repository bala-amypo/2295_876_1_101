package com.example.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.service.ConsumptionLogService;
import com.example.demo.model.ConsumptionLog;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@RestController
@RequestMapping("/api/consumption")
@Tag(name = "Consumption")
@SecurityRequirement(name="bearerAuth")
public class ConsumptionLogController {


private final ConsumptionLogService consumptionLogService;


public ConsumptionLogController(ConsumptionLogService consumptionLogService) {
this.consumptionLogService = consumptionLogService;
}


@PostMapping("/{stockRecordId}")
public ConsumptionLog log(@PathVariable Long stockRecordId,
@RequestBody ConsumptionLog log) {
return consumptionLogService.logConsumption(stockRecordId, log);
}


@GetMapping("/record/{stockRecordId}")
public List<ConsumptionLog> logs(@PathVariable Long stockRecordId) {
return consumptionLogService.getLogsByStockRecord(stockRecordId);
}


@GetMapping("/{id}")
public ConsumptionLog get(@PathVariable Long id) {
return consumptionLogService.getLog(id);
}
}