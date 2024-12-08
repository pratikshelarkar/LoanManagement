package com.loan.loan_manage.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loan.loan_manage.entities.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByCollateralTypeAccepted(Loan.CollateralType collateralType);

    List<Loan> findByInterestRateLessThan(BigDecimal interestRate);

    List<Loan> findByMaxTenureMonthsGreaterThan(Integer tenureMonths);

    List<Loan> findByLoanNameContaining(String loanName);
}
