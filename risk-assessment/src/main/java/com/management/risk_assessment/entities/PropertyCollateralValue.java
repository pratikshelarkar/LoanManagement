package com.management.risk_assessment.entities;

import lombok.Data;

@Data
public class PropertyCollateralValue extends CollateralMarketValue {

	
	public PropertyCollateralValue getCollateralValue() {
		PropertyCollateralValue obj = new PropertyCollateralValue();
		obj.setMarket_value((long) 600000);
		return obj;
		
	}
}
