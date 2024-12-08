package com.loan.loan_manage.entities;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationRequest {
    private Long customerId;
    private Long loanProductId;
    private BigDecimal loanAmount;
    private Integer tenureMonths;

}