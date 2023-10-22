package fr.ekwateur.capricorne.service;

import fr.ekwateur.capricorne.model.Energy;
import fr.ekwateur.capricorne.model.EnergyType;
import fr.ekwateur.capricorne.model.IndividualCustomer;
import fr.ekwateur.capricorne.model.ProCustomer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PricingServiceTest {

    private static Stream<Arguments> expectPricingAndIndividualCustomerValues() {
        return Stream.of(
                Arguments.of(ZERO.setScale(2), EnergyType.ELECTRICITY, ZERO),
                Arguments.of(ZERO.setScale(2), EnergyType.GAZ, ZERO),
                Arguments.of(new BigDecimal("11.50"), EnergyType.GAZ, new BigDecimal("100")),
                Arguments.of(new BigDecimal("12.10"), EnergyType.ELECTRICITY, new BigDecimal("100"))
        );
    }


    @ParameterizedTest
    @MethodSource("expectPricingAndIndividualCustomerValues")
    public void should_return_calculated_pricing_for_individual_customer(BigDecimal expectedResult, EnergyType energyType, BigDecimal consumedAmount) {
        // Given
        var individualCustomer = new IndividualCustomer("EKW123", new Energy(energyType, consumedAmount), "Mr", "firstName", "lastName");

        //When
        var customerPricing = PricingService.calculatePricingForGivenIndividualCustomer(individualCustomer);

        // Then
        assertEquals(expectedResult, customerPricing);
    }

    private static Stream<Arguments> expectPricingAndProCustomerValues() {
        return Stream.of(
                Arguments.of(ZERO.setScale(2), EnergyType.ELECTRICITY, ZERO, new BigDecimal(999999)),
                Arguments.of(ZERO.setScale(2), EnergyType.GAZ, ZERO, new BigDecimal(999999)),
                Arguments.of(new BigDecimal("11.80"), EnergyType.ELECTRICITY, new BigDecimal("100"), new BigDecimal(999999)),
                Arguments.of(new BigDecimal("11.30"), EnergyType.GAZ, new BigDecimal("100"), new BigDecimal(999999)),
                Arguments.of(new BigDecimal("11.80"), EnergyType.ELECTRICITY, new BigDecimal("100"), new BigDecimal(1000000)),
                Arguments.of(new BigDecimal("11.30"), EnergyType.GAZ, new BigDecimal("100"), new BigDecimal(1000000)),
                Arguments.of(new BigDecimal("11.40"), EnergyType.ELECTRICITY, new BigDecimal("100"), new BigDecimal(1000001)),
                Arguments.of(new BigDecimal("11.10"), EnergyType.GAZ, new BigDecimal("100"), new BigDecimal(1000001))
        );
    }


    @ParameterizedTest
    @MethodSource("expectPricingAndProCustomerValues")
    public void should_return_calculated_pricing_for_individual_customer(BigDecimal expectedResult, EnergyType energyType, BigDecimal consumedAmount, BigDecimal annualSales) {
        // Given
        var proCustomer = new ProCustomer("EKW123", new Energy(energyType, consumedAmount), 1234, "socialReason", annualSales);

        //When
        var customerPricing = PricingService.calculatePricingForGivenProCustomer(proCustomer);

        // Then
        assertEquals(expectedResult, customerPricing);
    }
}
