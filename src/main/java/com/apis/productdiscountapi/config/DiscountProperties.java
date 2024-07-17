package com.apis.productdiscountapi.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "discount")
public class DiscountProperties {

    private List<Integer> amountThresholds;
    private List<BigDecimal> amountValues;
    private List<Integer> percentageThresholds;
    private List<BigDecimal> percentageValues;

    @PostConstruct
    public void init() {
        System.out.println("Amount Thresholds: " + amountThresholds);
        System.out.println("Amount Values: " + amountValues);
        System.out.println("Percentage Thresholds: " + percentageThresholds);
        System.out.println("Percentage Values: " + percentageValues);
    }

    public List<Integer> getAmountThresholds() {
        return amountThresholds;
    }

    public void setAmountThresholds(List<Integer> amountThresholds) {
        this.amountThresholds = amountThresholds;
    }

    public List<BigDecimal> getAmountValues() {
        return amountValues;
    }

    public void setAmountValues(List<BigDecimal> amountValues) {
        this.amountValues = amountValues;
    }

    public List<Integer> getPercentageThresholds() {
        return percentageThresholds;
    }

    public void setPercentageThresholds(List<Integer> percentageThresholds) {
        this.percentageThresholds = percentageThresholds;
    }

    public List<BigDecimal> getPercentageValues() {
        return percentageValues;
    }

    public void setPercentageValues(List<BigDecimal> percentageValues) {
        this.percentageValues = percentageValues;
    }
}
