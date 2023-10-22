package fr.ekwateur.capricorne.model;

import java.math.BigDecimal;

public enum PriceThreshold {
    FIRST_THRESHOLD(new BigDecimal(0.118), new BigDecimal(0.113)),
    SECOND_THRESHOLD(new BigDecimal(0.114), new BigDecimal(0.111));

    private final BigDecimal unitaryElectricityPrice;
    private final BigDecimal unitaryGazPrice;

    PriceThreshold(BigDecimal unitaryElectricityPrice, BigDecimal unitaryGazPrice) {
        this.unitaryElectricityPrice = unitaryElectricityPrice;
        this.unitaryGazPrice = unitaryGazPrice;
    }

    public BigDecimal getUnitaryGazPrice() {
        return this.unitaryGazPrice;
    }

    public BigDecimal getUnitaryElectricityPrice() {
        return this.unitaryElectricityPrice;
    }

}
