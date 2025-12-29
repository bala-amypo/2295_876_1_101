package com.example.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.service.StockRecordService;
import com.example.demo.model.StockRecord;
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
@RequestMapping("/api/stocks")
@Tag(name = "StockRecord")
@SecurityRequirement(name="bearerAuth")
public class StockRecordController {


@Autowired private StockRecordService stockRecordService;


// public StockRecordController(StockRecordService stockRecordService) {
// this.stockRecordService = stockRecordService;
// }


@PostMapping("/{productId}/{warehouseId}")
public StockRecord create(@PathVariable Long productId,
@PathVariable Long warehouseId,
@RequestBody StockRecord record) {
return stockRecordService.createStockRecord(productId, warehouseId, record);
}


@GetMapping("/product/{productId}")
public List<StockRecord> byProduct(@PathVariable Long productId) {
return stockRecordService.getRecordsBy_product(productId);
}


@GetMapping("/warehouse/{warehouseId}")
public List<StockRecord> byWarehouse(@PathVariable Long warehouseId) {
return stockRecordService.getRecordsByWarehouse(warehouseId);
}


@GetMapping("/{id}")
public StockRecord get(@PathVariable Long id) {
return stockRecordService.getStockRecord(id);
}
}