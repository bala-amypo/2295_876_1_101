package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.entity.StockRecord;

public interface StockRecordRepository extends JpaRepository<StockRecord, Long> {
}
