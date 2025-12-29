package com.example.demo.service.impl;

import com.example.demo.model.StockRecord;
import com.example.demo.service.StockRecordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockRecordServiceImpl implements StockRecordService {

    @Override
    public StockRecord createStockRecord(Long productId, Long warehouseId, StockRecord record) {
        return record;
    }

    @Override
    public StockRecord getStockRecord(Long id) {
        return null;
    }

    @Override
    public List<StockRecord> getRecordsBy_product(Long productId) {
        return new ArrayList<>();
    }
}
