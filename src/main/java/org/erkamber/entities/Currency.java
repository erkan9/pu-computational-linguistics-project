package org.erkamber.entities;

import lombok.*;

import javax.validation.constraints.DecimalMax;

@Getter
@Setter
public class Currency {

    private String currency;

    private double currencyValue;

    public Currency(String currency, double currencyValue) {
        this.currency = currency;
        this.currencyValue = currencyValue;
    }

    @Override
    public String toString() {
        return currency + "-" + currencyValue;
    }
}