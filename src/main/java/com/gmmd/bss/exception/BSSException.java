/**
 *
 * Copyright (c) 2013 GMM GRAMMY(DIGITAL DOMAIN). All Rights Reserved.
 * 
 */
package com.gmmd.bss.exception;

import com.gmmd.bss.exception.util.ErrorMessageHelper;
import com.gmmd.bss.exception.util.LogMonitoringHelper;

public class BSSException extends Exception {

	static final long serialVersionUID = 1L;
	protected String errorCode;
	protected String errorMessage;
	protected int severity;
	
	public static final String DEFALUT_ERROR_CODE = "110001";

	/**
	 * 
	 */
	public BSSException() {
		this(null, DEFALUT_ERROR_CODE, null);
	}

	/**
	 * @param message
	 */
	public BSSException(String message) {
		this(null,DEFALUT_ERROR_CODE, new String[]{message});
	}

	/**
	 * @param cause
	 */
	public BSSException(Throwable cause) {
		this(cause, DEFALUT_ERROR_CODE, null);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BSSException(Throwable cause, String message) {
		this(cause, DEFALUT_ERROR_CODE, new String[]{message});
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param msgParams
	 */
	public BSSException(String errorCode, Object[] msgParams) {
		this(null, errorCode, msgParams);
	}
	
	/**
	 * @param message
	 * @param msgParams
	 */
	public BSSException(Throwable cause, String errorCode, Object[] msgParams) {
		super(cause);
		if(errorCode == null || "".equalsIgnoreCase(errorCode)){
			errorCode = DEFALUT_ERROR_CODE;
		}
		this.errorCode = errorCode;
		this.errorMessage = ErrorMessageHelper.getErrorMessage(errorCode, msgParams);
		try{
			this.severity = Integer.parseInt(errorCode.substring(1,2));
		} catch (NumberFormatException e){
			this.severity = Integer.parseInt(DEFALUT_ERROR_CODE.substring(1,2));
		}
		LogMonitoringHelper.monitoringAlert(this.severity, this.errorMessage);
	}
	
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the severity
	 */
	public int getSeverity() {
		return severity;
	}

	/**
	 * @param severity
	 *            the severity to set
	 */
	public void setSeverity(int severity) {
		this.severity = severity;
	}

	@Override
	public String getMessage() {
		return errorCode +" : "+errorMessage;
	}

}
