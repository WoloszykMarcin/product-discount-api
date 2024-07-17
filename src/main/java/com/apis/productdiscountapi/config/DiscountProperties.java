package com.apis.productdiscountapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "discount")
public class DiscountProperties {

    private static final Logger logger = LoggerFactory.getLogger(DiscountProperties.class);

    private List<Integer> amountThresholds;
    private List<BigDecimal> amountValues;
    private List<Integer> percentageThresholds;
    private List<BigDecimal> percentageValues;

    @PostConstruct
    public void init() {
        logger.info("Amount Thresholds: {}", amountThresholds);
        logger.info("Amount Values: {}", amountValues);
        logger.info("Percentage Thresholds: {}", percentageThresholds);
        logger.info("Percentage Values: {}", percentageValues);
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
