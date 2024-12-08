package com.loan.loan_manage.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CollateralRequest {
    private Long loanId;
    private Long collateralId;
    private String ownerName;
    private Collateral.CollateralType collateralType;
    private BigDecimal collateralValue;
    private PropertyType propertyType;

    private String ownerPanNumber;

    private String addressLine1;

    private String city;

    private String state;

    private BigDecimal totalAreaSqFt;

    private BigDecimal ratePerSqFt;

    private BigDecimal currentMarketValue;

    private BigDecimal depreciationRate;

    private Integer propertyAgeYears;

    public enum PropertyType {
        RESIDENTIAL, 
        COMMERCIAL, 
        AGRICULTURAL, 
        INDUSTRIAL
    }
    private String bankName;

    private String accountNumber;

    private AccountType accountType;

    private BigDecimal depositAmount;

    private BigDecimal currentBalance;

    private BigDecimal interestRate;

    private Integer lockPeriodMonths;

    private LocalDate depositDate;

    private LocalDate maturityDate;

    public enum AccountType {
        FIXED_DEPOSIT, 
        RECURRING_DEPOSIT, 
        SAVINGS, 
        CURRENT
    }
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	public Long getCollateralId() {
		return collateralId;
	}
	public void setCollateralId(Long collateralId) {
		this.collateralId = collateralId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Collateral.CollateralType getCollateralType() {
		return collateralType;
	}
	public void setCollateralType(Collateral.CollateralType collateralType) {
		this.collateralType = collateralType;
	}
	public BigDecimal getCollateralValue() {
		return collateralValue;
	}
	public void setCollateralValue(BigDecimal collateralValue) {
		this.collateralValue = collateralValue;
	}
	public PropertyType getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}
	public String getOwnerPanNumber() {
		return ownerPanNumber;
	}
	public void setOwnerPanNumber(String ownerPanNumber) {
		this.ownerPanNumber = ownerPanNumber;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public BigDecimal getTotalAreaSqFt() {
		return totalAreaSqFt;
	}
	public void setTotalAreaSqFt(BigDecimal totalAreaSqFt) {
		this.totalAreaSqFt = totalAreaSqFt;
	}
	public BigDecimal getRatePerSqFt() {
		return ratePerSqFt;
	}
	public void setRatePerSqFt(BigDecimal ratePerSqFt) {
		this.ratePerSqFt = ratePerSqFt;
	}
	public BigDecimal getCurrentMarketValue() {
		return currentMarketValue;
	}
	public void setCurrentMarketValue(BigDecimal currentMarketValue) {
		this.currentMarketValue = currentMarketValue;
	}
	public BigDecimal getDepreciationRate() {
		return depreciationRate;
	}
	public void setDepreciationRate(BigDecimal depreciationRate) {
		this.depreciationRate = depreciationRate;
	}
	public Integer getPropertyAgeYears() {
		return propertyAgeYears;
	}
	public void setPropertyAgeYears(Integer propertyAgeYears) {
		this.propertyAgeYears = propertyAgeYears;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public BigDecimal getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}
	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	public Integer getLockPeriodMonths() {
		return lockPeriodMonths;
	}
	public void setLockPeriodMonths(Integer lockPeriodMonths) {
		this.lockPeriodMonths = lockPeriodMonths;
	}
	public LocalDate getDepositDate() {
		return depositDate;
	}
	public void setDepositDate(LocalDate depositDate) {
		this.depositDate = depositDate;
	}
	public LocalDate getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(LocalDate maturityDate) {
		this.maturityDate = maturityDate;
	}


    
}

