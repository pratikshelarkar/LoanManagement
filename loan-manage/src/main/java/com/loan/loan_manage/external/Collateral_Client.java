package com.loan.loan_manage.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.loan.loan_manage.entities.CollateralRequest;

@FeignClient(
	    name = "collateral", 
	    url = "http://localhost:8086",
	    path = "/api/collaterals/"
	)
public interface Collateral_Client {
	@PostMapping("/saveCollaterals")
	ResponseEntity<?> saveCollaterals(@RequestBody CollateralRequest collateralRequest);
}