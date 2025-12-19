package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.entity.PredictionRule;

public interface PredictionRuleRepository extends JpaRepository<PredictionRule, Long> {
}
