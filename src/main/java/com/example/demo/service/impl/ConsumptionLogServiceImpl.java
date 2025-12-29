package com.example.demo.service.impl;

import com.example.demo.model.ConsumptionLog;
import com.example.demo.service.ConsumptionLogService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumptionLogServiceImpl implements ConsumptionLogService {

    @Override
    public ConsumptionLog logConsumption(Long stockRecordId, ConsumptionLog log) {
        return log;
    }

    @Override
    public List<ConsumptionLog> getLogsByStockRecord(Long stockRecordId) {
        return new ArrayList<>();
    }
}
