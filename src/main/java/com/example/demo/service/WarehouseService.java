package com.example.demo.service;

import com.example.demo.model.Warehouse;
import com.example.demo.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository repository;

    public WarehouseService(WarehouseRepository repository) {
        this.repository = repository;
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        return repository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        return repository.findAll();
    }

    public Warehouse getWarehouse(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
    }
}
