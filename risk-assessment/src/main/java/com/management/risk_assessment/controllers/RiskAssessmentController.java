package com.management.risk_assessment.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.risk_assessment.entities.CashDepositCollateralValue;
import com.management.risk_assessment.entities.CashDepositRisk;
import com.management.risk_assessment.entities.CollateralMarketValue;
import com.management.risk_assessment.entities.CollateralRisk;
import com.management.risk_assessment.entities.PropertyCollateralValue;
import com.management.risk_assessment.entities.PropertyRisk;
import com.management.risk_assessment.entities.calculateRiskPercentage;
import com.management.risk_assessment.external.GetCollateralsClient;

@RestController
@RequestMapping("/api/risk")
public class RiskAssessmentController {

	@Autowired
	GetCollateralsClient oGetCollateralsClient;

	@GetMapping("/getCollateralRisk")
	public List<CollateralRisk> getCollateralRisk(@RequestParam(required = true) Long loanId) {
		ResponseEntity<?> responseEntity = oGetCollateralsClient.getCollateralsByLoanId(loanId);
		List<Map<String, Object>> olist = (List<Map<String, Object>>) responseEntity.getBody();

		List<CollateralRisk> oListCollateralRisk = new ArrayList<>();
		for (Map<String, Object> map : olist) {
			CollateralRisk oCollateralRisk = new CollateralRisk();
			if (map.get("collateralType").equals(CollateralType.PROPERTY.toString())) {
				CollateralMarketValue oCollateralMarketValue = new PropertyCollateralValue();

				calculateRiskPercentage obj = new PropertyRisk();
				oCollateralRisk.setMarket_value(new PropertyCollateralValue().getCollateralValue().getMarket_value());
				oCollateralRisk.setRisk_percentage(obj.calculateRisk(oCollateralMarketValue));
				oCollateralRisk.setAssessed_date(LocalDate.parse(String.valueOf(map.get("pledgedDate"))));

			} else if (map.get("collateralType").equals(CollateralType.CASH_DEPOSIT.toString())) {
				CollateralMarketValue oCollateralMarketValue = new CashDepositCollateralValue();
				oCollateralRisk.setMarket_value(oCollateralMarketValue.getIntrest_rate().longValue());
				oCollateralRisk.setAssessed_date(LocalDate.parse(String.valueOf(map.get("pledgedDate"))));
				calculateRiskPercentage obj = new CashDepositRisk();
				oCollateralRisk.setRisk_percentage(obj.calculateRisk(oCollateralMarketValue));

			}
			oListCollateralRisk.add(oCollateralRisk);
		}
		return oListCollateralRisk;

	}

	public enum CollateralType {
		PROPERTY, CASH_DEPOSIT, OTHER
	}

}
