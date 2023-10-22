package fr.ekwateur.capricorne.service;

import fr.ekwateur.capricorne.model.Energy;
import fr.ekwateur.capricorne.model.IndividualCustomer;
import fr.ekwateur.capricorne.model.PriceThreshold;
import fr.ekwateur.capricorne.model.ProCustomer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static fr.ekwateur.capricorne.model.EnergyType.GAZ;

@Component
public class PricingService {

    private static final BigDecimal UNITARY_GAZ_PRICE_INDIV = new BigDecimal(0.115);
    private static final BigDecimal UNITARY_ELECTRICITY_PRICE_INDIV = new BigDecimal(0.121);
    private static final BigDecimal ANNUAL_SALES_THRESHOLD = new BigDecimal(1000000);
    private static final int SCALE = 2;

    public static BigDecimal calculatePricingForGivenIndividualCustomer(IndividualCustomer individualCustomer) {
        Energy consumedEnergy = individualCustomer.consumedEnergy();
        if (GAZ.equals(consumedEnergy.type())) {
            return multiplyAndScaleUp(consumedEnergy.amount(), UNITARY_GAZ_PRICE_INDIV);
        }
        return multiplyAndScaleUp(consumedEnergy.amount(), UNITARY_ELECTRICITY_PRICE_INDIV);
    }

    public static BigDecimal calculatePricingForGivenProCustomer(ProCustomer proCustomer) {
        Energy consumedEnergy = proCustomer.consumedEnergy();
        // TODO if annual sales = 1 000 000 € we take the expensive price (to be checked)
        if (ANNUAL_SALES_THRESHOLD.compareTo(proCustomer.annualSales()) >= 0) {
            return calculatePricingGivenThreshold(consumedEnergy, PriceThreshold.FIRST_THRESHOLD);
        }
        return calculatePricingGivenThreshold(consumedEnergy, PriceThreshold.SECOND_THRESHOLD);

    }

    private static BigDecimal calculatePricingGivenThreshold(Energy consumedEnergy, PriceThreshold priceThreshold) {
        return switch (consumedEnergy.type()) {
            case GAZ -> multiplyAndScaleUp(consumedEnergy.amount(), priceThreshold.getUnitaryGazPrice());
            case ELECTRICITY -> multiplyAndScaleUp(consumedEnergy.amount(), priceThreshold.getUnitaryElectricityPrice());
        };
    }

    private static BigDecimal multiplyAndScaleUp(BigDecimal amount, BigDecimal unitaryPrice) {
        return amount.multiply(unitaryPrice).setScale(SCALE, RoundingMode.HALF_UP);
    }
}
