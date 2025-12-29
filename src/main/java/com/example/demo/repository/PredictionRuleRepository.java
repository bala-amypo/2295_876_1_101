package com.example.demo.repository;

import com.example.demo.model.PredictionRule;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PredictionRuleRepository extends JpaRepository<PredictionRule,Long>{

}