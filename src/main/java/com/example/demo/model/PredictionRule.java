package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredictionRule {
    @Id @GeneratedValue
    private Long id;
    private String ruleName;
    private int averageDaysWindow;
    private int minDailyUsage;
    private int maxDailyUsage;
    private LocalDateTime createdAt;
}
