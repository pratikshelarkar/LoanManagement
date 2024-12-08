package com.loan.loan_manage.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loan.loan_manage.entities.Collateral;
import com.loan.loan_manage.entities.CollateralRequest;
import com.loan.loan_manage.entities.Customer;
import com.loan.loan_manage.entities.CustomerLoan;
import com.loan.loan_manage.entities.Loan;
import com.loan.loan_manage.entities.LoanApplication;
import com.loan.loan_manage.entities.LoanApplicationRequest;
import com.loan.loan_manage.exceptions.ErrorConstants;
import com.loan.loan_manage.exceptions.MyException;
import com.loan.loan_manage.external.Collateral_Client;
import com.loan.loan_manage.repositories.CollateralRepository;
import com.loan.loan_manage.repositories.CustomerLoanRepository;
import com.loan.loan_manage.repositories.CustomerRepository;
import com.loan.loan_manage.repositories.LoanApplicationRepository;
import com.loan.loan_manage.repositories.LoanRepository;
import com.loan.loan_manage.services.SecurityService;

@RestController
@RequestMapping("/api/loans")
public class LoanManagementController {

	@Autowired
	SecurityService securityService;
	
	@Autowired
    private CustomerLoanRepository customerLoanRepository;

    @Autowired
    private CollateralRepository collateralRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanRepository loanRepository;
    
    @Autowired
    ModelMapper modelMapper;
    
    @Autowired
    Collateral_Client oCollateral_Client;

    @GetMapping("/getLoanDetails")
    public ResponseEntity<?> getLoanDetails(
        @RequestParam(required = true) Long loanId, 
        @RequestParam(required = true) Long customerId
    ) {
        return getLoanDetail(loanId, customerId);
    }

	private ResponseEntity<?> getLoanDetail(Long loanId, Long customerId) {
		try {
            Optional<CustomerLoan> customerLoan = customerLoanRepository.findByLoanIdAndCustomerId(loanId, customerId);
            
            if (customerLoan.isEmpty()) {
                throw new MyException("ERR001",ErrorConstants.NO_LOAN_FOUND_MSG);
            }

            Map<String, Object> loanDetails = new HashMap<>();
            CustomerLoan loan = customerLoan.get();
            
            loanDetails.put("loanId", loan.getLoanId());
            loanDetails.put("sanctionedLoanAmount", loan.getLoanPrincipal());
            loanDetails.put("tenure", loan.getTenureMonths());
            loanDetails.put("interestRate", loan.getInterestRate());
            loanDetails.put("monthlyEmi", loan.getMonthlyEmi());
            
            if (loan.getCollateral() != null) {
                loanDetails.put("collateralId", loan.getCollateral().getCollateralId());
                loanDetails.put("collateralType", loan.getCollateral().getType());
                loanDetails.put("collateralValue", loan.getCollateral().getValue());
            }

            return ResponseEntity.ok(loanDetails);
        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Error retrieving loan details: " + e.getMessage());
        	throw new MyException("ERR002",ErrorConstants.ERR_RETRIVING_LOAN_DETAILS);
        }
	}

    @PostMapping("/saveCollaterals")
    public ResponseEntity<?> saveCollaterals(
        @RequestBody CollateralRequest collateralRequest
    ) throws AuthenticationException {
//    	if(securityService.isAuthenticated()) {
    		return saveCollateral(collateralRequest);
//    	}else {
//    		throw new AuthenticationException();
//    	}
    }

	private ResponseEntity<?> saveCollateral(CollateralRequest collateralRequest) {
		try {
            Collateral collateral = new Collateral();
            collateral.setCollateralId(collateralRequest.getCollateralId());
            collateral.setType(collateralRequest.getCollateralType());
            collateral.setValue(collateralRequest.getCollateralValue());
            

            Collateral savedCollateral = collateralRepository.save(collateral);
            
            Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(collateralRequest.getLoanId());
            loanApplication.get().setCollateral(savedCollateral);
            loanApplicationRepository.save(loanApplication.get());
            
            
            
            collateralRequest.setCollateralId(savedCollateral.getCollateralId());
            oCollateral_Client.saveCollaterals(collateralRequest);

            return ResponseEntity.status(HttpStatus.CREATED)
                .body("Collateral saved successfully");
        } catch (Exception e) {
            throw new MyException("ERR003",ErrorConstants.ERR_SAVING_COLLATERAL_DETAILS);
        }
	}

    @PostMapping("/applyLoan")
    public ResponseEntity<?> applyLoan(
        @RequestBody LoanApplicationRequest loanApplicationRequest
    ) {
        try {
            Optional<Customer> customer = customerRepository.findById(loanApplicationRequest.getCustomerId());
            Optional<Loan> loanProduct = loanRepository.findById(loanApplicationRequest.getLoanProductId());

            if (customer.isEmpty() || loanProduct.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer or Loan Product not found");
            }
            
            LoanApplication loanApplication = new LoanApplication();
            loanApplication.setCustomer(customer.get());
            loanApplication.setLoanProduct(loanProduct.get());
            loanApplication.setLoanAmount(loanApplicationRequest.getLoanAmount());
            loanApplication.setTenureMonths(loanApplicationRequest.getTenureMonths());
            loanApplication.setApplicationDate(LocalDate.now());
            loanApplication.setStatus(LoanApplication.ApplicationStatus.PENDING);

            LoanApplication savedApplication = loanApplicationRepository.save(loanApplication);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("Loan Application Submitted. Application ID: " + savedApplication.getApplicationId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error applying for loan: " + e.getMessage());
        }
    }

    @GetMapping("/getLoanApplicationStatus")
    public ResponseEntity<?> getLoanApplicationStatus(
        @RequestParam(required = true) Long applicationId
    ) {
        try {
            Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(applicationId);
            
            if (loanApplication.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Loan Application not found");
            }

            return ResponseEntity.ok(loanApplication.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving loan application status: " + e.getMessage());
        }
    }

    @PutMapping("/approveLoanApplication")
    public ResponseEntity<?> approveLoanApplication(
        @RequestParam(required = true) Long applicationId
    ) {
        try {
            Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(applicationId);
            Optional<Customer> customer = customerRepository.findById(loanApplication.get().getCustomer().getCustomerId());
            Optional<Loan> loanProduct = loanRepository.findById(loanApplication.get().getLoanProduct().getLoanProductId());

            if (loanApplication.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Loan Application not found");
            }

            LoanApplication application = loanApplication.get();
            
            application.setStatus(LoanApplication.ApplicationStatus.APPROVED);
            loanApplicationRepository.save(application);
            
            CollateralRequest oCollateralRequest = new CollateralRequest();
            oCollateralRequest.setCollateralId(application.getCollateral().getCollateralId());
            oCollateralRequest.setCollateralType(application.getCollateral().getType());
            oCollateralRequest.setCollateralValue(application.getCollateral().getValue());
            oCollateralRequest.setLoanId(application.getApplicationId());
            Optional<Collateral> oCollateralObj = collateralRepository.findById(application.getCollateral().getCollateralId());
//            Optional<CustomerLoan> customerLoan = customerLoanRepository.findById(oCollateralObj.get().getCustomerLoan().getLoanId());

            
            Collateral oCollateral = new Collateral();
            oCollateral.setCollateralId(application.getCollateral().getCollateralId());
            oCollateral.setLoanApplication(loanApplication.get());
            oCollateral.setType(application.getCollateral().getType());
            oCollateral.setValue(application.getCollateral().getValue());
            oCollateral.setDescription("");
            
            
            CustomerLoan custLoan = new CustomerLoan();
            custLoan.setCollateral(oCollateralObj.get());
            custLoan.setCustomer(customer.get());
            custLoan.setEndDate(LocalDate.now().plusDays(1825));
            custLoan.setInterestRate(new BigDecimal(loanProduct.get().getInterestRate().toBigInteger()));
            custLoan.setLoanPrincipal(loanApplication.get().getLoanAmount());
            custLoan.setLoanProduct(loanProduct.get());
            int intrest = (loanApplication.get().getLoanAmount().intValue()/100)*loanProduct.get().getInterestRate().intValue();
            int afterIntrest = loanApplication.get().getLoanAmount().intValue()+intrest;
            custLoan.setMonthlyEmi(BigDecimal.valueOf(afterIntrest/loanApplication.get().getTenureMonths()));
            custLoan.setStartDate(LocalDate.now());
            custLoan.setTenureMonths(loanApplication.get().getTenureMonths());
            
            customerLoanRepository.save(custLoan);
//            oCollateral_Client.saveCollaterals(oCollateralRequest);

            return ResponseEntity.ok("Loan Application Approved");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error approving loan application: " + e.getMessage());
        }
    }

    @PutMapping("/rejectLoanApplication")
    public ResponseEntity<?> rejectLoanApplication(
        @RequestParam(required = true) Long applicationId
    ) {
        try {
            Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(applicationId);
            
            if (loanApplication.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Loan Application not found");
            }

            LoanApplication application = loanApplication.get();
            application.setStatus(LoanApplication.ApplicationStatus.REJECTED);
            loanApplicationRepository.save(application);

            return ResponseEntity.ok("Loan Application Rejected");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error rejecting loan application: " + e.getMessage());
        }
    }
    
    @PostMapping("/createLoanProduct")
    public ResponseEntity<?> createLoanProduct(
        @RequestBody Loan loan
    ) {
        try {
        	Loan loanObj = modelMapper.map(loan, Loan.class);
        	
        	Loan savedLoanProduct = loanRepository.save(loanObj);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("Loan Product created. Loan Product ID: " + savedLoanProduct.getLoanProductId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error applying for loan: " + e.getMessage());
        }
    }
    
    @PostMapping("/createCustomer")
    public ResponseEntity<?> createCustomer(
        @RequestBody Customer customer
    ) {
        try {
        	Customer customerObj = modelMapper.map(customer, Customer.class);
        	
        	Customer savedCustomer= customerRepository.save(customerObj);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("Customer created. Customer ID: " + savedCustomer.getCustomerId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error applying for loan: " + e.getMessage());
        }
    }
    
}
