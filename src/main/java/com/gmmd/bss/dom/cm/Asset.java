/**
 *
 * Copyright (c) 2013 GMM GRAMMY(DIGITAL DOMAIN). All Rights Reserved.
 * 
 */
package com.gmmd.bss.dom.cm;


import com.gmmd.bss.dom.DomObject;

public class Asset extends DomObject {

	private static final long serialVersionUID = 1L;
	
	private Integer assetId;
	private String assetStatus;

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public String getAssetStatus() {
		return assetStatus;
	}

	public void setAssetStatus(String assetStatus) {
		this.assetStatus = assetStatus;
	}
	
}
