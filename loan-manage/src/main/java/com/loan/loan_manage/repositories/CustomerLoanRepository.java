package com.loan.loan_manage.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.loan.loan_manage.entities.CustomerLoan;

@Repository
public interface CustomerLoanRepository extends JpaRepository<CustomerLoan, Long>{

	
	// Find customer loan by loan ID and customer ID
    @Query("SELECT cl FROM CustomerLoan cl WHERE cl.loanId = :loanId AND cl.customer.customerId = :customerId")
    Optional<CustomerLoan> findByLoanIdAndCustomerId(
        @Param("loanId") Long loanId, 
        @Param("customerId") Long customerId
    );

    // Find all loans for a specific customer
    List<CustomerLoan> findByCustomerCustomerId(Long customerId);

    // Find loans by loan product
    List<CustomerLoan> findByLoanProductLoanProductId(Long loanProductId);

    // Find loans within a specific principal range
    List<CustomerLoan> findByLoanPrincipalBetween(BigDecimal minPrincipal, BigDecimal maxPrincipal);
}
