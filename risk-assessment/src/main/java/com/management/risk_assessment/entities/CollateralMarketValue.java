package com.management.risk_assessment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class CollateralMarketValue {

	private Double intrest_rate = 0.0;
	private Long market_value;
	
	//TODO : refresh below values using file is pending

	public abstract CollateralMarketValue getCollateralValue();
	
	enum LandType{
		COMMERCIAL,
		RESIDENTIAL
	}
}
