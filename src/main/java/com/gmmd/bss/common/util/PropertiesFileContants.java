/**
 *
 * Copyright (c) 2013 GMM GRAMMY(DIGITAL DOMAIN). All Rights Reserved.
 */
package com.gmmd.bss.common.util;

/**
 * @author prachyawut
 * @version 1.00
 */
public enum PropertiesFileContants {

	/* resources/app-conf
	 * 
	 * 
	 * 
	 */
	
	/* resources
	 * 
	 * 
	 * 
	 */
	
	//CPN
	CPN_LOOKUP_COUPON_GENERATOR("/resources/cpn_lookup_coupon_generator.properties", true),
	CPN_BUSINESS_BATCH_CONFIG("/resources/cpn_business_batch_config.properties",true);
	
	private String propertyName;
	private String propertyPath;
	private boolean isErrorCase;
	
	private PropertiesFileContants(String propertyPath, boolean isErrorCase) {
		this.propertyName = this.name();
		this.propertyPath = propertyPath;
		this.isErrorCase = isErrorCase;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyPath() {
		return propertyPath;
	}
	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}
	public boolean isErrorCase() {
		return isErrorCase;
	}
	public void setErrorCase(boolean isErrorCase) {
		this.isErrorCase = isErrorCase;
	}

}
