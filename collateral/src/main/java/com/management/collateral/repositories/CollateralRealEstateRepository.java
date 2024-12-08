package com.management.collateral.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.collateral.entities.CollateralRealEstate;
import com.management.collateral.entities.CollateralRealEstate.PropertyType;

@Repository
public interface CollateralRealEstateRepository extends JpaRepository<CollateralRealEstate, PropertyType>{

}
