package com.example.demo.service.impl;

import com.example.demo.model.Warehouse;
import com.example.demo.service.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Override
    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouse;
    }

    @Override
    public Warehouse getWarehouse(Long id) {
        return null;
    }

    @Override
    public List<Warehouse> getAllWarehouses() {
        return new ArrayList<>();
    }
}
