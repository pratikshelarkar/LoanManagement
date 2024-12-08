package com.management.risk_assessment.entities;

public class CashDepositRisk implements calculateRiskPercentage {

	private double currentIntrest = 12.0; 
	@Override
	public double calculateRisk(CollateralMarketValue obj) {
		if(obj.getIntrest_rate()< currentIntrest) {
			return 10.0;
		}else {
			return 0.0;
		}
	}

}
