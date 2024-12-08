package com.management.risk_assessment.entities;

public class CashDepositCollateralValue extends CollateralMarketValue{

	public CashDepositCollateralValue getCollateralValue() {
		
		CashDepositCollateralValue obj = new CashDepositCollateralValue();
		
		obj.setIntrest_rate(11.0);
		return obj;
	}
}
