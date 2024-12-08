package com.management.risk_assessment.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
	    name = "collateral", 
	    url = "http://localhost:8086",
	    path = "/api/collaterals"
	)
	public interface GetCollateralsClient {
	    @GetMapping("/getCollateralsByLoanId")
	    ResponseEntity<?> getCollateralsByLoanId(@RequestParam("loanId") Long loanId);
	}