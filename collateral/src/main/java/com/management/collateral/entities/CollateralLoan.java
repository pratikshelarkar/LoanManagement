package com.management.collateral.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "collateral_loan")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollateralLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collateral_loan_id")
    private Long collateralLoanId;

    @Column(name = "loan_id", nullable = false)
    private Long loanId;

    @Column(name = "collateral_id", nullable = false)
    private Long collateralId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "collateral_type", nullable = false)
    private CollateralType collateralType;

    @Column(name = "collateral_value", nullable = false)
    private BigDecimal collateralValue;

    @Column(name = "pledged_date", nullable = false)
    private LocalDate pledgedDate;

    @Column(name = "owner_name", nullable = true)
    private String ownerName;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "collateral_status")
    private CollateralStatus collateralStatus;

    public enum CollateralType {
        PROPERTY, 
        CASH_DEPOSIT, 
        OTHER
    }

    public enum CollateralStatus {
        PLEDGED, 
        VERIFIED, 
        RELEASED, 
        DISPUTED
    }

}