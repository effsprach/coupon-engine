/**
 *
 * Copyright (c) 2013 GMM GRAMMY(DIGITAL DOMAIN). All Rights Reserved.
 */
package com.gmmd.bss.exception;

/**
 * @author prachyawut
 * @version 1.00 7 ат.б. 2556
 */
public class BusinessException extends BSSException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String errorCode, Object[] msgParams) {
		super(errorCode, msgParams);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(Throwable cause, String message) {
		super(cause, message);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(Throwable cause, String errorCode,
			Object[] msgParams) {
		super(cause, errorCode, msgParams);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}


	
	

}
