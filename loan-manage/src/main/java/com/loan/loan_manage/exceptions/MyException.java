package com.loan.loan_manage.exceptions;

import org.springframework.stereotype.Component;

@Component
public class MyException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errCode;
    private String errMsg;
 
    public MyException(String errMsg) { 
    	super(errMsg);
    }
    
    public MyException() { 
    }
 
    public MyException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
 
    public String getErrCode() {
        return errCode;
    }
 
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
 
    public String getErrMsg() {
        return errMsg;
    }
 
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }   

}
