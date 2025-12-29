package com.example.demo.controller;

import com.example.demo.model.Warehouse;
import com.example.demo.service.WarehouseService;
import org.springframework.web.bind.annotation.*;

@RestController("warehouseController")
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService service;

    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @PostMapping
    public Warehouse create(@RequestBody Warehouse warehouse) {
        return service.createWarehouse(warehouse);
    }

    @GetMapping("/{id}")
    public Warehouse get(@PathVariable Long id) {
        return service.getWarehouse(id);
    }
}
