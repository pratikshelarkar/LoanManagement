package com.loan.loan_manage.exceptions;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler {
	
	
	@ExceptionHandler(IllegalArgumentException.class) 
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
		
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Illegal parameter passed: "+e.getMessage());
    }
	
	@ExceptionHandler(MyException.class) 
    public ResponseEntity<String> noLoanFoundException(MyException e) {
	
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrCode()+":"+e.getMessage());
    }
	@ExceptionHandler(AuthenticationException.class) 
    public ResponseEntity<String> noAuthentication(AuthenticationException e) {
	
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Authentication :"+e.getMessage());
    }
	
}
