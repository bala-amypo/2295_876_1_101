package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "stock_records",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "warehouse_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Column(nullable = false)
    private Integer currentQuantity;

    @Column(nullable = false)
    private Integer reorderThreshold;

    private LocalDateTime lastUpdated;
}
