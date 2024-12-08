package com.management.collateral.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "collateral_cash_deposit")
@PrimaryKeyJoinColumn(name = "collateral_loan_id")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollateralCashDeposit extends CollateralLoan {
    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "deposit_amount", nullable = false)
    private BigDecimal depositAmount;

    @Column(name = "current_balance", nullable = false)
    private BigDecimal currentBalance;

    @Column(name = "interest_rate", nullable = false)
    private BigDecimal interestRate;

    @Column(name = "lock_period_months", nullable = false)
    private Integer lockPeriodMonths;

    @Column(name = "deposit_date", nullable = false)
    private LocalDate depositDate;

    @Column(name = "maturity_date", nullable = false)
    private LocalDate maturityDate;

    public enum AccountType {
        FIXED_DEPOSIT, 
        RECURRING_DEPOSIT, 
        SAVINGS, 
        CURRENT
    }

}
