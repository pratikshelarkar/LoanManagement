package com.loan.loan_manage.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loan.loan_manage.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    List<Customer> findByCreditScoreGreaterThan(Integer creditScore);

    List<Customer> findByAnnualIncomeBetween(BigDecimal minIncome, BigDecimal maxIncome);

}
