package com.loan.loan_manage.entities;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loan_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_product_id")
    private Long loanProductId;

    @Column(name = "loan_name", nullable = false)
    private String loanName;

    @Column(name = "max_loan_eligible", nullable = false)
    private BigDecimal maximumLoanEligible;

    @Column(name = "interest_rate", nullable = false)
    private BigDecimal interestRate;

    @Column(name = "max_tenure_months", nullable = false)
    private Integer maxTenureMonths;

    @Enumerated(EnumType.STRING)
    @Column(name = "collateral_type")
    private CollateralType collateralTypeAccepted;

    @OneToMany(mappedBy = "loanProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CustomerLoan> customerLoans;

    @OneToMany(mappedBy = "loanProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanApplication> loanApplications;

    public enum CollateralType {
        PROPERTY, VEHICLE, FIXED_DEPOSIT, NONE
    }
    
}