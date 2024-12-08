package com.management.risk_assessment.entities;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollateralRisk  {
	Double risk_percentage;
	LocalDate assessed_date;
	Long sanctioned_loan;
	Long market_value;
}
