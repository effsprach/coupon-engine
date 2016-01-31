/**
 *
 * Copyright (c) 2013 GMM GRAMMY(DIGITAL DOMAIN). All Rights Reserved.
 */
package com.gmmd.bss.cm.bs;

import java.util.List;

import com.gmmd.bss.dom.cm.Asset;
import com.gmmd.bss.dom.cm.CustomerAccount;
import com.gmmd.bss.exception.BSSException;
import com.gmmd.bss.exception.BusinessException;
/**
 * @author prachyawut
 * @version 1.00
 */
public interface IAssetService {
	
	public Asset storeAsset(Asset asset) throws BSSException;
	
	boolean updateAssetStatusByCaId(CustomerAccount ca, List<Asset> assetList) throws BusinessException;

}
