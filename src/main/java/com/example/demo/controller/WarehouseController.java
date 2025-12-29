package com.example.demo.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.service.WarehouseService;
import com.example.demo.model.Warehouse;
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
@RequestMapping("/api/warehouses")
@Tag(name = "Warehouse")
@SecurityRequirement(name="bearerAuth")
public class WarehouseController {


private final WarehouseService warehouseService;


public WarehouseController(WarehouseService warehouseService) {
this.warehouseService = warehouseService;
}


@PostMapping
public Warehouse create(@RequestBody Warehouse warehouse) {
return warehouseService.createWarehouse(warehouse);
}


@GetMapping
public List<Warehouse> getAll() {
return warehouseService.getAllWarehouses();
}


@GetMapping("/{id}")
public Warehouse get(@PathVariable Long id) {
return warehouseService.getWarehouse(id);
}
}