package com.example.demo.controller;

import com.example.demo.model.Warehouse;
import com.example.demo.service.WarehouseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService service;

    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @PostMapping
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return service.createWarehouse(warehouse);
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouse(@PathVariable Long id) {
        return service.getWarehouse(id);
    }

    @GetMapping
    public List<Warehouse> getAllWarehouses() {
        return service.getAllWarehouses();
    }
}
