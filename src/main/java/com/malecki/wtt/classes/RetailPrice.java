package com.malecki.wtt.classes;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetailPrice {
    private double amount;
    private double amountInMicros;
    private String currencyCode;

    public double getAmountInMicros() {
        return amountInMicros;
    }

    public void setAmountInMicros(double amountInMicros) {
        this.amountInMicros = amountInMicros;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
