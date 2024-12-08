package com.management.collateral.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.collateral.entities.CollateralCashDeposit;
import com.management.collateral.entities.CollateralLoan;
import com.management.collateral.entities.CollateralRealEstate;
import com.management.collateral.entities.CollateralRequest;
import com.management.collateral.repositories.CollateralCashDepositRepository;
import com.management.collateral.repositories.CollateralLoanRepository;
import com.management.collateral.repositories.CollateralRealEstateRepository;

@RestController
@RequestMapping("/api/collaterals")
public class CollateralManagementController {
	

	@Autowired
    private CollateralLoanRepository collateralLoanRepository;

    @Autowired
    private CollateralRealEstateRepository collateralRealEstateRepository;

    @Autowired
    private CollateralCashDepositRepository collateralCashDepositRepository;

    @GetMapping("/getCollaterals")
    public ResponseEntity<?> getCollaterals(
        @RequestParam(required = true) Long loanId
    ) {
        try {
            List<CollateralLoan> collaterals = collateralLoanRepository
                .findByLoanId(loanId);
            
            if (collaterals.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No collaterals found for given Loan ID and Customer ID");
            }

            List<Map<String, Object>> collateralDetails = new ArrayList<>();
            
            for (CollateralLoan collateral : collaterals) {
                Map<String, Object> collateralInfo = new HashMap<>();
                
                collateralInfo.put("collateralId", collateral.getCollateralId());
                collateralInfo.put("collateralType", collateral.getCollateralType());
                collateralInfo.put("collateralValue", collateral.getCollateralValue());
                collateralInfo.put("pledgedDate", collateral.getPledgedDate());
                
                if (collateral instanceof CollateralRealEstate) {
                    CollateralRealEstate realEstate = (CollateralRealEstate) collateral;
                    collateralInfo.put("propertyType", realEstate.getPropertyType());
                    collateralInfo.put("address", 
                        realEstate.getAddressLine1() + ", " + 
                        realEstate.getCity() + ", " + 
                        realEstate.getState() + " - " + 
                        realEstate.getPincode()
                    );
                    collateralInfo.put("totalArea", realEstate.getTotalAreaSqFt());
                    collateralInfo.put("marketValue", realEstate.getCurrentMarketValue());
                }
                else if (collateral instanceof CollateralCashDeposit) {
                    CollateralCashDeposit cashDeposit = (CollateralCashDeposit) collateral;
                    collateralInfo.put("bankName", cashDeposit.getBankName());
                    collateralInfo.put("accountType", cashDeposit.getAccountType());
                    collateralInfo.put("depositAmount", cashDeposit.getDepositAmount());
                    collateralInfo.put("interestRate", cashDeposit.getInterestRate());
                    collateralInfo.put("lockPeriod", cashDeposit.getLockPeriodMonths());
                }
                
                collateralDetails.add(collateralInfo);
            }

            return ResponseEntity.ok(collateralDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving collaterals: " + e.getMessage());
        }
    }

    @PostMapping("/saveCollaterals")
    public ResponseEntity<?> saveCollaterals(
        @RequestBody CollateralRequest collateralRequest
    ) {
        try {
            CollateralLoan collateral;
            
            switch (collateralRequest.getCollateralType()) {
                case PROPERTY:
                    CollateralRealEstate realEstate = new CollateralRealEstate();
                    realEstate.setPropertyType(collateralRequest.getPropertyType());
                    realEstate.setAddressLine1(collateralRequest.getAddressLine1());
                    realEstate.setAddressLine2(collateralRequest.getAddressLine2());
                    realEstate.setCity(collateralRequest.getCity());
                    realEstate.setState(collateralRequest.getState());
                    realEstate.setPincode(collateralRequest.getPincode());
                    realEstate.setTotalAreaSqFt(collateralRequest.getTotalAreaSqFt());
                    realEstate.setCurrentMarketValue(collateralRequest.getCurrentMarketValue());
                    realEstate.setRatePerSqFt(collateralRequest.getRatePerSqFt());
                    collateral = realEstate;
                    break;
                
                case CASH_DEPOSIT:
                    CollateralCashDeposit cashDeposit = new CollateralCashDeposit();
                    cashDeposit.setBankName(collateralRequest.getBankName());
                    cashDeposit.setAccountNumber(collateralRequest.getAccountNumber());
                    cashDeposit.setAccountType(collateralRequest.getAccountType());
                    cashDeposit.setDepositAmount(collateralRequest.getDepositAmount());
                    cashDeposit.setInterestRate(collateralRequest.getInterestRate());
                    cashDeposit.setLockPeriodMonths(collateralRequest.getLockPeriodMonths());
                    cashDeposit.setDepositDate(LocalDate.now());
                    cashDeposit.setMaturityDate(LocalDate.now().plusMonths(collateralRequest.getLockPeriodMonths()));
                    collateral = cashDeposit;
                    break;
                
                default:
                    return ResponseEntity.badRequest()
                        .body("Unsupported Collateral Type");
            }

            collateral.setLoanId(collateralRequest.getLoanId());
            collateral.setCollateralId(collateralRequest.getCollateralId());
            collateral.setCollateralType(collateralRequest.getCollateralType());
            collateral.setCollateralValue(collateralRequest.getCollateralValue());
            collateral.setPledgedDate(LocalDate.now());
            collateral.setOwnerName(collateralRequest.getOwnerName());
            collateral.setDescription(collateralRequest.getDescription());
            collateral.setCollateralStatus(CollateralLoan.CollateralStatus.PLEDGED);

            if (collateral instanceof CollateralRealEstate) {
                collateralRealEstateRepository.save((CollateralRealEstate) collateral);
            } else if (collateral instanceof CollateralCashDeposit) {
                collateralCashDepositRepository.save((CollateralCashDeposit) collateral);
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                .body("Collateral saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error saving collateral: " + e.getMessage());
        }
    }

    
    @GetMapping("/getCollateralsByLoanId")
    public ResponseEntity<?> getCollateralsByLoanId(
        @RequestParam(required = true) Long loanId
    ) {
            List<CollateralLoan> collaterals = collateralLoanRepository
                .findByLoanId(loanId);
            
            if (collaterals.isEmpty()) {
                return null;
            }

            List<Map<String, Object>> collateralDetails = new ArrayList<>();
            
            for (CollateralLoan collateral : collaterals) {
                Map<String, Object> collateralInfo = new HashMap<>();
                
                collateralInfo.put("collateralId", collateral.getCollateralId());
                collateralInfo.put("collateralType", collateral.getCollateralType());
                collateralInfo.put("collateralValue", collateral.getCollateralValue());
                collateralInfo.put("pledgedDate", collateral.getPledgedDate());
                
                if (collateral instanceof CollateralRealEstate) {
                    CollateralRealEstate realEstate = (CollateralRealEstate) collateral;
                    collateralInfo.put("propertyType", realEstate.getPropertyType());
                    collateralInfo.put("address", 
                        realEstate.getAddressLine1() + ", " + 
                        realEstate.getCity() + ", " + 
                        realEstate.getState() + " - " + 
                        realEstate.getPincode()
                    );
                    collateralInfo.put("totalArea", realEstate.getTotalAreaSqFt());
                    collateralInfo.put("marketValue", realEstate.getCurrentMarketValue());
                }
                else if (collateral instanceof CollateralCashDeposit) {
                    CollateralCashDeposit cashDeposit = (CollateralCashDeposit) collateral;
                    collateralInfo.put("bankName", cashDeposit.getBankName());
                    collateralInfo.put("accountType", cashDeposit.getAccountType());
                    collateralInfo.put("depositAmount", cashDeposit.getDepositAmount());
                    collateralInfo.put("interestRate", cashDeposit.getInterestRate());
                    collateralInfo.put("lockPeriod", cashDeposit.getLockPeriodMonths());
                }
                
                collateralDetails.add(collateralInfo);
            }
            return ResponseEntity.ok(collateralDetails);
        
    }
    
}
