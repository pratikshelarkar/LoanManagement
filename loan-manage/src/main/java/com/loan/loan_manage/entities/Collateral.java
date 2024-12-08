package com.loan.loan_manage.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.loan.loan_manage.entities.Customer.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "collaterals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collateral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collateral_id")
    private Long collateralId;

    @Enumerated(EnumType.STRING)
    @Column(name = "collateral_type", nullable = false)
    private CollateralType type;

    @Column(name = "value", nullable = false)
    private BigDecimal value;
    
    @Column(name = "owner_name", nullable = true)
    private String ownerName;

    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "collateral")
    private CustomerLoan customerLoan;

    @OneToOne(mappedBy = "collateral")
    private LoanApplication loanApplication;

    public enum CollateralType {
        PROPERTY, CASH_DEPOSIT, OTHER
    }

}
