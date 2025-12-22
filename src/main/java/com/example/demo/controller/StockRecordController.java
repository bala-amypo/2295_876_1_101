package com.example.demo.controller;

import com.example.demo.model.StockRecord;
import com.example.demo.service.StockRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@Tag(name = "Stock Records", description = "Stock record management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class StockRecordController {

    private final StockRecordService stockRecordService;

    public StockRecordController(StockRecordService stockRecordService) {
        this.stockRecordService = stockRecordService;
    }

    @PostMapping("/{productId}/{warehouseId}")
    @Operation(summary = "Create a new stock record")
    public ResponseEntity<StockRecord> createStockRecord(@PathVariable Long productId,
                                                         @PathVariable Long warehouseId,
                                                         @RequestBody StockRecord record) {
        StockRecord created = stockRecordService.createStockRecord(productId, warehouseId, record);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get stock records by product ID")
    public ResponseEntity<List<StockRecord>> getRecordsByProduct(@PathVariable Long productId) {
        List<StockRecord> records = stockRecordService.getRecordsBy_product(productId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/warehouse/{warehouseId}")
    @Operation(summary = "Get stock records by warehouse ID")
    public ResponseEntity<List<StockRecord>> getRecordsByWarehouse(@PathVariable Long warehouseId) {
        List<StockRecord> records = stockRecordService.getRecordsByWarehouse(warehouseId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get stock record by ID")
    public ResponseEntity<StockRecord> getStockRecord(@PathVariable Long id) {
        StockRecord record = stockRecordService.getStockRecord(id);
        return ResponseEntity.ok(record);
    }
}