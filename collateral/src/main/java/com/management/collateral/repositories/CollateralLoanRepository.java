package com.management.collateral.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.management.collateral.entities.CollateralLoan;

@Repository
public interface CollateralLoanRepository extends JpaRepository<CollateralLoan, Long>{
	List<CollateralLoan> findByLoanId(long loanId);
	
//    List<CollateralLoan> findByLoanIdAndCustomerId(
//        @Param("loanId") Long loanId
//    );
//    
//    List<CollateralLoan> findByLoanId(
//        @Param("loanId") Long loanId
//    );

}
