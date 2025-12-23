package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class PredictionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int daysAhead;

    public PredictionRule() {
    }

    public PredictionRule(int daysAhead) {
        this.daysAhead = daysAhead;
    }

    public Long getId() {
        return id;
    }

    public int getDaysAhead() {
        return daysAhead;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDaysAhead(int daysAhead) {
        this.daysAhead = daysAhead;
    }
}
