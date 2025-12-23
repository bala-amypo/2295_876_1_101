package com.example.demo.service;

import com.example.demo.model.Warehouse;
import com.example.demo.repository.WarehouseRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository repo;

    public WarehouseService(WarehouseRepository repo) {
        this.repo = repo;
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        if (warehouse.getLocation() == null || warehouse.getLocation().isBlank())
            throw new IllegalArgumentException("location cannot be empty");

        repo.findByWarehouseName(warehouse.getWarehouseName()).ifPresent(w -> {
            throw new IllegalArgumentException("Warehouse name must be unique");
        });

        warehouse.setCreatedAt(LocalDateTime.now());
        return repo.save(warehouse);
    }

    public Warehouse getWarehouse(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));
    }

    public List<Warehouse> getAllWarehouses() {
        return repo.findAll();
    }
}
