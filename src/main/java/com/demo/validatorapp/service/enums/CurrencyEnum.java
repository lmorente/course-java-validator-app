package com.demo.validatorapp.service.enums;

public enum CurrencyEnum {
    EURO("EUR");

    public String isoCode;

    CurrencyEnum(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getIsoCode() {
        return isoCode;
    }
}
