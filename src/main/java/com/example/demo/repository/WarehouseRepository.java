package com.example.demo.repository;

import com.example.demo.model.Warehouse;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Long>{

}