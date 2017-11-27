package com.anveshak.wealthmanagerapp;

import java.math.BigDecimal;

/**
 * Created by Tamajeet on 27-11-2017.
 */

public class ScoreDetailsDto {
    private String customerId;
    private String merchantType;
    private String month;
    private Integer score;
    private Double amount;

    public String getCustomerId() {
        return customerId;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public String getMonth() {
        return month;
    }

    public Integer getScore() {
        return score;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
