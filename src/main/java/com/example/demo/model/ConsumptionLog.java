package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "consumption_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_record_id", nullable = false)
    private StockRecord stockRecord;

    @Min(value = 1, message = "Consumed quantity must be > 0")
    @Column(nullable = false)
    private Integer consumedQuantity;

    @Column(nullable = false)
    private LocalDate consumedDate;
}