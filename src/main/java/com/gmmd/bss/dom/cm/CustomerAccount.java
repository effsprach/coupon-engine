package com.gmmd.bss.dom.cm;

import com.gmmd.bss.dom.DomObject;

public class CustomerAccount extends DomObject {

	private static final long serialVersionUID = 1L;
	
	private Integer customerAccountId;

	public Integer getCustomerAccountId() {
		return customerAccountId;
	}

	public void setCustomerAccountId(Integer customerAccountId) {
		this.customerAccountId = customerAccountId;
	}

}
