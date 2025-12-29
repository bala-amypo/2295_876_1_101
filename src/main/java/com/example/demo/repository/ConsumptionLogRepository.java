package com.example.demo.repository;

import com.example.demo.model.ConsumptionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumptionLogRepository extends JpaRepository<ConsumptionLog, Long> {
    List<ConsumptionLog> findByStockRecordIdAndConsumedDateBetween(Long id, LocalDate s, LocalDate e);
    List<ConsumptionLog> findByStockRecordIdOrderByConsumedDateDesc(Long id);
}
