package com.loan.loan_manage.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loan.loan_manage.entities.LoanApplication;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByCustomerCustomerId(Long customerId);

    List<LoanApplication> findByStatus(LoanApplication.ApplicationStatus status);

    List<LoanApplication> findByApplicationDateBetween(LocalDate startDate, LocalDate endDate);

    List<LoanApplication> findByLoanProductLoanProductId(Long loanProductId);

}