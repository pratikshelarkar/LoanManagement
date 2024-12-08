package com.management.collateral.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.collateral.entities.CollateralCashDeposit;
import com.management.collateral.entities.CollateralCashDeposit.AccountType;

@Repository
public interface CollateralCashDepositRepository extends JpaRepository<CollateralCashDeposit, AccountType>{

}
