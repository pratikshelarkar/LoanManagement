package com.management.risk_assessment.entities;

public class PropertyRisk implements calculateRiskPercentage{

	private double currentMarketValue = 10000000.0; 
	@Override
	public double calculateRisk(CollateralMarketValue obj) {
		
		if(obj.getCollateralValue().getMarket_value()< currentMarketValue) {
			return 10.0;
		}else {
			return 0.0;
		}
	}

}
