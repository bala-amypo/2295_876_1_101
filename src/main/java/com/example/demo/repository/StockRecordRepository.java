package com.example.demo.repository;

import com.example.demo.model.StockRecord;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
@Repository
public interface StockRecordRepository extends JpaRepository<StockRecord, Long> {
List<StockRecord> findByProductId(Long productId);
List<StockRecord> findByWarehouseId(Long warehouseId);
}