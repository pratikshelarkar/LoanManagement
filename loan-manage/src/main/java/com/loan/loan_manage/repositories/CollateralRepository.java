package com.loan.loan_manage.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loan.loan_manage.entities.Collateral;

@Repository
public interface CollateralRepository extends JpaRepository<Collateral, Long> {
    List<Collateral> findByType(Collateral.CollateralType type);

    List<Collateral> findByValueGreaterThan(BigDecimal value);

    Optional<Collateral> findByCustomerLoanLoanId(Long loanId);
}