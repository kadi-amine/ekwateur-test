package fr.ekwateur.capricorne.model;

import java.math.BigDecimal;

public record ProCustomer(String reference, Energy consumedEnergy, Integer siretNumber, String socialReason, BigDecimal annualSales) {}
