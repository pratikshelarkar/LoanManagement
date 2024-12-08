package com.management.collateral.entities;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollateralRequest {
    private Long loanId;
    private Long collateralId;
    private CollateralLoan.CollateralType collateralType;
    private BigDecimal collateralValue;
    private String ownerName;
    private String description;

    private CollateralRealEstate.PropertyType propertyType;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pincode;
    private BigDecimal totalAreaSqFt;
    private BigDecimal currentMarketValue;
    private BigDecimal ratePerSqFt;

    private String bankName;
    private String accountNumber;
    private CollateralCashDeposit.AccountType accountType;
    private BigDecimal depositAmount;
    private BigDecimal interestRate;
    private Integer lockPeriodMonths;

}
