package com.management.collateral.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "collateral_real_estate")
@PrimaryKeyJoinColumn(name = "collateral_loan_id")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollateralRealEstate extends CollateralLoan {
    @Column(name = "property_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Column(name = "owner_pan_number")
    private String ownerPanNumber;

    @Column(name = "address_line1", nullable = true)
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "pincode", nullable = true)
    private String pincode;

    @Column(name = "total_area_sqft", nullable = false)
    private BigDecimal totalAreaSqFt;

    @Column(name = "rate_per_sqft", nullable = false)
    private BigDecimal ratePerSqFt;

    @Column(name = "current_market_value", nullable = true)
    private BigDecimal currentMarketValue;

    @Column(name = "depreciation_rate", nullable = true)
    private BigDecimal depreciationRate;

    @Column(name = "property_age_years")
    private Integer propertyAgeYears;

    @Column(name = "is_clear_title")
    private Boolean isClearTitle;

    public enum PropertyType {
        RESIDENTIAL, 
        COMMERCIAL, 
        AGRICULTURAL, 
        INDUSTRIAL
    }

}