package com.example.demo.repository;

import com.example.demo.model.ConsumptionLog;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsumptionLogRepository extends JpaRepository<ConsumptionLog, Long> {

    List<ConsumptionLog> findByStockRecordId(Long stockRecordId);

    List<ConsumptionLog> findByStockRecordIdAndConsumedDateBetween(
            Long stockRecordId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<ConsumptionLog> findByStockRecordIdOrderByConsumedDateDesc(Long stockRecordId);
}
