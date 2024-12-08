package com.loan.loan_manage.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loan_applications")
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_product_id", nullable = false)
    private Loan loanProduct;

    @Column(name = "loan_amount", nullable = false)
    private BigDecimal loanAmount;

    @Column(name = "tenure_months", nullable = false)
    private Integer tenureMonths;

    @OneToOne
    @JoinColumn(name = "collateral_id")
    private Collateral collateral;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    public enum ApplicationStatus {
        PENDING, APPROVED, REJECTED, UNDER_REVIEW
    }
    
//    public Customer getLazyCustomer() {
//        Hibernate.initialize(this.customer);
//        return this.customer;
//    }
//    public Loan getLazyLoanProduct() {
//        Hibernate.initialize(this.loanProduct);
//        return this.loanProduct;
//    }
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Loan getLoanProduct() {
		return loanProduct;
	}
	public void setLoanProduct(Loan loanProduct) {
		this.loanProduct = loanProduct;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Integer getTenureMonths() {
		return tenureMonths;
	}
	public void setTenureMonths(Integer tenureMonths) {
		this.tenureMonths = tenureMonths;
	}
	public Collateral getCollateral() {
		return collateral;
	}
	public void setCollateral(Collateral collateral) {
		this.collateral = collateral;
	}
	public ApplicationStatus getStatus() {
		return status;
	}
	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}
	public LocalDate getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(LocalDate applicationDate) {
		this.applicationDate = applicationDate;
	}
	@Override
	public String toString() {
		return "LoanApplication [applicationId=" + applicationId + ", customer=" + customer + ", loanProduct="
				+ loanProduct + ", loanAmount=" + loanAmount + ", tenureMonths=" + tenureMonths + ", collateral="
				+ collateral + ", status=" + status + ", applicationDate=" + applicationDate + "]";
	}

    
}

